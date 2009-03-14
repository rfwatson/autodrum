DrumMachine {
  classvar <ntracks, <names, <attributes;
  var <view;
  var <currentpattern, <currentAttributeIndex;
  
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
    view                  = DrumMachineView.new(this);

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
}
