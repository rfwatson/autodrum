DrumMachine {
  classvar <ntracks, <names, <attributes;
  var <view, buffers, <samplepaths;
  var <currentpattern, <currentAttributeIndex;
  
  var isplaying, <tempo, routine, clock, currentBeatIndex;
  
  *initClass {
    ntracks = 8;
    names   = [
      "KICK",
      "BASS",
      "SNARE",
      "CLAP",
      "PERC 1",
      "PERC 2",
      "CHH",
      "OHH"
    ];

    attributes = [
      "PROBs",
      "AMPs",
      "LPFs"
    ];
  }
  
  *new {
    ^super.new.init;
  }
  
  init {
    currentpattern        = DrumPattern.new(this);

    samplepaths           = "/Developer/Projects/gcproject/samples/*.wav".pathMatch;

    buffers               = samplepaths.collect{ |path|
      Buffer.read(Server.default, path);
    };

    view                  = DrumMachineView.new(this);
    
    isplaying             = false;
    tempo                 = 130 / 60;
    clock                 = TempoClock.new(tempo);
    
    routine = Routine {
      inf.do { |index|
        currentBeatIndex = index % 16;

        Server.default.bind {
          currentpattern.tracks.do { |track, trackIndex|
            var prob, amp, lpf;
            prob = track[0][currentBeatIndex];
            amp  = track[1][currentBeatIndex];
            lpf  = track[2][currentBeatIndex];
            
            
            if(prob.coin) {
              Synth(\beat, [bufnum: buffers[trackIndex], amp: amp, lpf: lpf]);
            }
          }
        };


        0.25.wait;
      }

    };

    currentAttributeIndex = 0;
    
    this.addDependant(view);
  }
  
  currentAttribute {
    ^attributes[currentAttributeIndex]
  }
  
  nextAttribute {
    currentAttributeIndex = (currentAttributeIndex + 1) % attributes.size;
    this.changed(\attr);
  }
  
  play {
    if(isplaying.not) {
      currentBeatIndex = 0;
      isplaying = true;
      routine.play(clock, quant: 4);
    }
  }
  
  stop {
    if(isplaying) {
      isplaying = false;
    }
  }
  
  tempo_{ |bpm|
    tempo = bpm / 60;
    clock.tempo = tempo;
  }
}
