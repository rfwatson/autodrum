DrumMachineView {
  var <machine, <w, <tracksview, <toolbar, <attrtext, pads;
  
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
    
    tracksview    = VLayoutView(w, Rect(5, 110, 1300, 650))
      ;
      
    pads        = List[];    

    machine.currentpattern.tracks.do { |track, trackIndex|
      var row   = List[];
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
        var pad = Pad.new(hbox, Rect(0, 0, 70, 70))
          .hitIndex_(hitIndex)
          .trackIndex_(trackIndex)
          .machine_(machine)
          ;
          
        row.add(pad);
      };
      
      pads.add(row);
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