AttrSelector : SCUserView {
  var <>machine;
  
  *viewClass {
    ^SCUserView
  }
  
  init { |theParent, someBounds|
    super.init(theParent, someBounds);

    this
      .relativeOrigin_(true)
      .canFocus_(false)
      .drawFunc_{ this.draw }
      ;
  }
  
  mouseDown {
    machine.nextAttribute;
    this.refresh;
  }
  
  draw {
    Pen.fillColor = Color.black;
    Pen.addRect(this.bounds);
    Pen.fillStroke;
    Pen.fillColor = Color.red;
    Pen.font = Font("Monaco", 28);
    Pen.stringCenteredIn(machine.currentAttribute, this.bounds);
    Pen.fillStroke;
  }
}
