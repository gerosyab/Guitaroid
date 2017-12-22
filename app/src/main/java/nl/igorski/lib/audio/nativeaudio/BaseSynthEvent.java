/* ----------------------------------------------------------------------------
 * This file was automatically generated by SWIG (http://www.swig.org).
 * Version 3.0.12
 *
 * Do not make changes to this file unless you know what you are doing--modify
 * the SWIG interface file instead.
 * ----------------------------------------------------------------------------- */

package nl.igorski.lib.audio.nativeaudio;

public class BaseSynthEvent extends BaseAudioEvent {
  private transient long swigCPtr;

  protected BaseSynthEvent(long cPtr, boolean cMemoryOwn) {
    super(MWEngineCoreJNI.BaseSynthEvent_SWIGUpcast(cPtr), cMemoryOwn);
    swigCPtr = cPtr;
  }

  protected static long getCPtr(BaseSynthEvent obj) {
    return (obj == null) ? 0 : obj.swigCPtr;
  }

  protected void finalize() {
    delete();
  }

  public synchronized void delete() {
    if (swigCPtr != 0) {
      if (swigCMemOwn) {
        swigCMemOwn = false;
        MWEngineCoreJNI.delete_BaseSynthEvent(swigCPtr);
      }
      swigCPtr = 0;
    }
    super.delete();
  }

  public BaseSynthEvent() {
    this(MWEngineCoreJNI.new_BaseSynthEvent__SWIG_0(), true);
  }

  public BaseSynthEvent(float aFrequency, int aPosition, float aLength, SynthInstrument aInstrument) {
    this(MWEngineCoreJNI.new_BaseSynthEvent__SWIG_1(aFrequency, aPosition, aLength, SynthInstrument.getCPtr(aInstrument), aInstrument), true);
  }

  public BaseSynthEvent(float aFrequency, SynthInstrument aInstrument) {
    this(MWEngineCoreJNI.new_BaseSynthEvent__SWIG_2(aFrequency, SynthInstrument.getCPtr(aInstrument), aInstrument), true);
  }

  public void setInstanceId(long value) {
    MWEngineCoreJNI.BaseSynthEvent_instanceId_set(swigCPtr, this, value);
  }

  public long getInstanceId() {
    return MWEngineCoreJNI.BaseSynthEvent_instanceId_get(swigCPtr, this);
  }

  public void setPosition(int value) {
    MWEngineCoreJNI.BaseSynthEvent_position_set(swigCPtr, this, value);
  }

  public int getPosition() {
    return MWEngineCoreJNI.BaseSynthEvent_position_get(swigCPtr, this);
  }

  public void setLength(float value) {
    MWEngineCoreJNI.BaseSynthEvent_length_set(swigCPtr, this, value);
  }

  public float getLength() {
    return MWEngineCoreJNI.BaseSynthEvent_length_get(swigCPtr, this);
  }

  public float getFrequency() {
    return MWEngineCoreJNI.BaseSynthEvent_getFrequency(swigCPtr, this);
  }

  public float getBaseFrequency() {
    return MWEngineCoreJNI.BaseSynthEvent_getBaseFrequency(swigCPtr, this);
  }

  public void setFrequency(float aFrequency) {
    MWEngineCoreJNI.BaseSynthEvent_setFrequency__SWIG_0(swigCPtr, this, aFrequency);
  }

  public void setFrequency(float aFrequency, boolean storeAsBaseFrequency) {
    MWEngineCoreJNI.BaseSynthEvent_setFrequency__SWIG_1(swigCPtr, this, aFrequency, storeAsBaseFrequency);
  }

  public void setCachedProps(CachedProperties value) {
    MWEngineCoreJNI.BaseSynthEvent_cachedProps_set(swigCPtr, this, CachedProperties.getCPtr(value), value);
  }

  public CachedProperties getCachedProps() {
    long cPtr = MWEngineCoreJNI.BaseSynthEvent_cachedProps_get(swigCPtr, this);
    return (cPtr == 0) ? null : new CachedProperties(cPtr, false);
  }

  public SWIGTYPE_p_SAMPLE_TYPE getPhaseForOscillator(int aOscillatorNum) {
    return new SWIGTYPE_p_SAMPLE_TYPE(MWEngineCoreJNI.BaseSynthEvent_getPhaseForOscillator(swigCPtr, this, aOscillatorNum), true);
  }

  public void setPhaseForOscillator(int aOscillatorNum, SWIGTYPE_p_SAMPLE_TYPE aPhase) {
    MWEngineCoreJNI.BaseSynthEvent_setPhaseForOscillator(swigCPtr, this, aOscillatorNum, SWIGTYPE_p_SAMPLE_TYPE.getCPtr(aPhase));
  }

  public void setLastWriteIndex(int value) {
    MWEngineCoreJNI.BaseSynthEvent_lastWriteIndex_set(swigCPtr, this, value);
  }

  public int getLastWriteIndex() {
    return MWEngineCoreJNI.BaseSynthEvent_lastWriteIndex_get(swigCPtr, this);
  }

  public void invalidateProperties(int aPosition, float aLength, SynthInstrument aInstrument) {
    MWEngineCoreJNI.BaseSynthEvent_invalidateProperties(swigCPtr, this, aPosition, aLength, SynthInstrument.getCPtr(aInstrument), aInstrument);
  }

  public void calculateBuffers() {
    MWEngineCoreJNI.BaseSynthEvent_calculateBuffers(swigCPtr, this);
  }

  public void mixBuffer(SWIGTYPE_p_AudioBuffer outputBuffer, int bufferPos, int minBufferPosition, int maxBufferPosition, boolean loopStarted, int loopOffset, boolean useChannelRange) {
    MWEngineCoreJNI.BaseSynthEvent_mixBuffer(swigCPtr, this, SWIGTYPE_p_AudioBuffer.getCPtr(outputBuffer), bufferPos, minBufferPosition, maxBufferPosition, loopStarted, loopOffset, useChannelRange);
  }

  public SWIGTYPE_p_AudioBuffer synthesize(int aBufferLength) {
    long cPtr = MWEngineCoreJNI.BaseSynthEvent_synthesize(swigCPtr, this, aBufferLength);
    return (cPtr == 0) ? null : new SWIGTYPE_p_AudioBuffer(cPtr, false);
  }

  public void unlock() {
    MWEngineCoreJNI.BaseSynthEvent_unlock(swigCPtr, this);
  }

}
