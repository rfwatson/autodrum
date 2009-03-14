Pad : SCUserView {
  var <>trackIndex, <>hitIndex, <>machine;
  
  var changing = false;
  var ystart, valstart;

  *viewClass { ^SCUserView }

  init { |theParent, someBounds|
    super.init(theParent, someBounds);

    this
      .relativeOrigin_(true)
      .canFocus_(false)
      .drawFunc = {
        this.draw;
      }
      ;
  }
  
  draw {
    var pattern = machine.currentpattern;
    var value;
    
    value  = pattern.tracks[trackIndex][machine.currentAttributeIndex][hitIndex];
    Pen.strokeColor = Color.black;

    if(hitIndex % 4 == 0) {
      Pen.width = 2;
      Pen.fillColor = Color(0.9, 0.9, 0.9 - value).blend(Color.red, value);
    } {
      Pen.fillColor = Color(1, 1, 1 - value).blend(Color.red, value);      
    };
    
    Pen.addRect(Rect(2, 2, this.bounds.width - 4, this.bounds.height - 4));
    Pen.fillStroke;
    
    if(changing) {
      Pen.font = Font("Monaco", 11);
      Pen.fillColor = Color.black;
      Pen.addRect(Rect(5, 5, 30, 14));
      Pen.fillStroke;
      Pen.fillColor = Color.white;
      Pen.stringAtPoint(value.round(0.01).asString, 5 @ 5);
      Pen.fillStroke;
    }
  }
  
  mouseDown { |x, y, mods, button, clickcount|
    var tracks   = machine.currentpattern.tracks;
    if(clickcount == 2) {
      var value = tracks[trackIndex][machine.currentAttributeIndex][hitIndex];
      if(value.asFloat == 1.0) {
        tracks[trackIndex][machine.currentAttributeIndex][hitIndex] = 0.0;
      } {
        tracks[trackIndex][machine.currentAttributeIndex][hitIndex] = 1.0;        
      }
    } {
      changing = true;
      ystart   = y;
      valstart = tracks[trackIndex][machine.currentAttributeIndex][hitIndex];
    };
    this.refresh;
  }
  
  mouseMove { |x, y, mods, button, clickcount|
    var tracks = machine.currentpattern.tracks;
    
    if(changing) {
      var amount = (ystart - y) / 150;
      tracks[trackIndex][machine.currentAttributeIndex][hitIndex] = (valstart + amount).clip(0.0, 1.0);
    };
    
    this.refresh;
  }
  
  mouseUp {
    changing = false;
    this.refresh;
  }
}
