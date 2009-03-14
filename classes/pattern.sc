DrumPattern {
  var <view, <machine;

  var <probs, <lpfs, <amps;
  var <probvariance, <lpfvariance, <ampvariance;
  
  var <tracks;
  
  *new { |machine|
    ^super.new.init(machine);
  }

  init { |aMachine|
    machine   = aMachine;
    tracks    = List[];
    
    DrumMachine.ntracks.do { |track|
      tracks.add([
        0.0 ! 16, // probs
        0.7 ! 16, // amps
        0.0 ! 16, // lpfs
      ])
    };
  }
}
