SynthDef(\beat) { |out=0, bufnum, amp=1.0|
  
  
  var playbuf = PlayBuf.ar(
    numChannels: 2,
    bufnum: bufnum,
    loop: 0
  );
  
  Out.ar(out, 
    MoogFF.ar(
      playbuf * amp.exprange(0.0001, 1),
      lpf.linexp(0, 1, 150, 20000)
  ));
  FreeSelfWhenDone.kr(playbuf);
}.load(s);

