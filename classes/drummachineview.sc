DrumMachineView {
  var <machine, <w, <tracksview, <toolbar, <attrtext;
  
  *new { |machine|
    ^super.new.init(machine);
  }
  
  init { |aMachine|
    machine       = aMachine;
    w             = Window("Autodrum", Rect(100, 500, 1320, 720));
        
    toolbar       = CompositeView(w, Rect(5, 5, 1300, 100))
      .background_(Color(0, 0.3, 0))
      ;
      
    AttrSelector(toolbar, Rect(5, 5, 120, 90))
      .machine_(machine)
      ;
      
    Button(toolbar, Rect(145, 5, 120, 90))
      .states_([
        ["Play", Color.white, Color(0, 0.3, 0)],
        ["Stop", Color.white, Color(0.3, 0, 0)]
      ])
      .font_(Font("Monaco", 28))
      .canFocus_(false)
      .action_{ |button|
        button.value.switch(
          0, {
            machine.stop;
          },
          1, {
            machine.play;
          }
        )
      }
      ;
      
    NumberBox(toolbar, Rect(285, 5, 100, 90))
      .font_(Font("Monaco", 28))
      .clipLo_(80)
      .clipHi_(180)
      .background_(Color.black)
      .normalColor_(Color.green)
      .typingColor_(Color.white)
      .value_(130.0)
      .canFocus_(false)
      .action_{ |box|
        machine.tempo = box.value;
      }
      ;
      
    StaticText(toolbar, Rect(395, 5, 80, 90))
      .font_(Font("Monaco", 28))
      .stringColor_(Color.grey)
      .string_("bpm")
      ;
    
    tracksview    = VLayoutView(w, Rect(5, 110, 1300, 650))
      ;
      

    machine.currentpattern.tracks.do { |track, trackIndex|
      var hbox  = HLayoutView(tracksview, Rect(5, 5, 1200, 70))
        .background_(Color(0.4,0.4,0.4))
        ;
                
      var probs, amps, lpfs;
      # probs, amps, lpfs = track;
      
      StaticText(hbox, Rect(0, 0, 120, 70))
        .string_(DrumMachine.names[trackIndex])
        .stringColor_(Color.green)
        .font_(Font("Monaco", 28))
        ;

      16.do { |hitIndex|
        Pad.new(hbox, Rect(0, 0, 70, 70))
          .hitIndex_(hitIndex)
          .trackIndex_(trackIndex)
          .machine_(machine)
          ;
      };
      
    };
     
    w.front;
  }
  
  update { |aMachine, what|
    {
      what.switch(
        \attr, {
          w.refresh;
        }
      )
    }.defer;
  }
}