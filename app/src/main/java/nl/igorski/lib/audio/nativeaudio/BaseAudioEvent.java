/* ----------------------------------------------------------------------------
 * This file was automatically generated by SWIG (http://www.swig.org).
 * Version 3.0.12
 *
 * Do not make changes to this file unless you know what you are doing--modify
 * the SWIG interface file instead.
 * ----------------------------------------------------------------------------- */

package nl.igorski.lib.audio.nativeaudio;

public class BaseAudioEvent {
  private transient long swigCPtr;
  protected transient boolean swigCMemOwn;

  protected BaseAudioEvent(long cPtr, boolean cMemoryOwn) {
    swigCMemOwn = cMemoryOwn;
    swigCPtr = cPtr;
  }

  protected static long getCPtr(BaseAudioEvent obj) {
    return (obj == null) ? 0 : obj.swigCPtr;
  }

  protected void finalize() {
    delete();
  }

  public synchronized void delete() {
    if (swigCPtr != 0) {
      if (swigCMemOwn) {
        swigCMemOwn = false;
        MWEngineCoreJNI.delete_BaseAudioEvent(swigCPtr);
      }
      swigCPtr = 0;
    }
  }

  public BaseAudioEvent(BaseInstrument instrument) {
    this(MWEngineCoreJNI.new_BaseAudioEvent__SWIG_0(BaseInstrument.getCPtr(instrument), instrument), true);
  }

  public BaseAudioEvent() {
    this(MWEngineCoreJNI.new_BaseAudioEvent__SWIG_1(), true);
  }

  public void mixBuffer(SWIGTYPE_p_AudioBuffer outputBuffer, int bufferPosition, int minBufferPosition, int maxBufferPosition, boolean loopStarted, int loopOffset, boolean useChannelRange) {
    MWEngineCoreJNI.BaseAudioEvent_mixBuffer(swigCPtr, this, SWIGTYPE_p_AudioBuffer.getCPtr(outputBuffer), bufferPosition, minBufferPosition, maxBufferPosition, loopStarted, loopOffset, useChannelRange);
  }

  public SWIGTYPE_p_AudioBuffer getBuffer() {
    long cPtr = MWEngineCoreJNI.BaseAudioEvent_getBuffer(swigCPtr, this);
    return (cPtr == 0) ? null : new SWIGTYPE_p_AudioBuffer(cPtr, false);
  }

  public void setBuffer(SWIGTYPE_p_AudioBuffer buffer, boolean destroyable) {
    MWEngineCoreJNI.BaseAudioEvent_setBuffer(swigCPtr, this, SWIGTYPE_p_AudioBuffer.getCPtr(buffer), destroyable);
  }

  public boolean hasBuffer() {
    return MWEngineCoreJNI.BaseAudioEvent_hasBuffer(swigCPtr, this);
  }

  public SWIGTYPE_p_AudioBuffer synthesize(int aBufferLength) {
    long cPtr = MWEngineCoreJNI.BaseAudioEvent_synthesize(swigCPtr, this, aBufferLength);
    return (cPtr == 0) ? null : new SWIGTYPE_p_AudioBuffer(cPtr, false);
  }

  public BaseInstrument getInstrument() {
    long cPtr = MWEngineCoreJNI.BaseAudioEvent_getInstrument(swigCPtr, this);
    return (cPtr == 0) ? null : new BaseInstrument(cPtr, false);
  }

  public void setInstrument(BaseInstrument aInstrument) {
    MWEngineCoreJNI.BaseAudioEvent_setInstrument(swigCPtr, this, BaseInstrument.getCPtr(aInstrument), aInstrument);
  }

  public void addToSequencer() {
    MWEngineCoreJNI.BaseAudioEvent_addToSequencer(swigCPtr, this);
  }

  public void removeFromSequencer() {
    MWEngineCoreJNI.BaseAudioEvent_removeFromSequencer(swigCPtr, this);
  }

  public void setIsSequenced(boolean value) {
    MWEngineCoreJNI.BaseAudioEvent_isSequenced_set(swigCPtr, this, value);
  }

  public boolean getIsSequenced() {
    return MWEngineCoreJNI.BaseAudioEvent_isSequenced_get(swigCPtr, this);
  }

  public void setSampleLength(int value) {
    MWEngineCoreJNI.BaseAudioEvent_setSampleLength(swigCPtr, this, value);
  }

  public void setSampleStart(int value) {
    MWEngineCoreJNI.BaseAudioEvent_setSampleStart(swigCPtr, this, value);
  }

  public void setSampleEnd(int value) {
    MWEngineCoreJNI.BaseAudioEvent_setSampleEnd(swigCPtr, this, value);
  }

  public int getSampleLength() {
    return MWEngineCoreJNI.BaseAudioEvent_getSampleLength(swigCPtr, this);
  }

  public int getSampleStart() {
    return MWEngineCoreJNI.BaseAudioEvent_getSampleStart(swigCPtr, this);
  }

  public int getSampleEnd() {
    return MWEngineCoreJNI.BaseAudioEvent_getSampleEnd(swigCPtr, this);
  }

  public void setStartPosition(float value) {
    MWEngineCoreJNI.BaseAudioEvent_setStartPosition(swigCPtr, this, value);
  }

  public void setEndPosition(float value) {
    MWEngineCoreJNI.BaseAudioEvent_setEndPosition(swigCPtr, this, value);
  }

  public void setDuration(float value) {
    MWEngineCoreJNI.BaseAudioEvent_setDuration(swigCPtr, this, value);
  }

  public float getStartPosition() {
    return MWEngineCoreJNI.BaseAudioEvent_getStartPosition(swigCPtr, this);
  }

  public float getEndPosition() {
    return MWEngineCoreJNI.BaseAudioEvent_getEndPosition(swigCPtr, this);
  }

  public float getDuration() {
    return MWEngineCoreJNI.BaseAudioEvent_getDuration(swigCPtr, this);
  }

  public void positionEvent(int startMeasure, int subdivisions, int offset) {
    MWEngineCoreJNI.BaseAudioEvent_positionEvent(swigCPtr, this, startMeasure, subdivisions, offset);
  }

  public int getReadPointer() {
    return MWEngineCoreJNI.BaseAudioEvent_getReadPointer(swigCPtr, this);
  }

  public boolean isLoopeable() {
    return MWEngineCoreJNI.BaseAudioEvent_isLoopeable(swigCPtr, this);
  }

  public void setLoopeable(boolean value) {
    MWEngineCoreJNI.BaseAudioEvent_setLoopeable(swigCPtr, this, value);
  }

  public boolean isDeletable() {
    return MWEngineCoreJNI.BaseAudioEvent_isDeletable(swigCPtr, this);
  }

  public void setDeletable(boolean value) {
    MWEngineCoreJNI.BaseAudioEvent_setDeletable(swigCPtr, this, value);
  }

  public boolean isEnabled() {
    return MWEngineCoreJNI.BaseAudioEvent_isEnabled(swigCPtr, this);
  }

  public void setEnabled(boolean value) {
    MWEngineCoreJNI.BaseAudioEvent_setEnabled(swigCPtr, this, value);
  }

  public void lock() {
    MWEngineCoreJNI.BaseAudioEvent_lock(swigCPtr, this);
  }

  public void unlock() {
    MWEngineCoreJNI.BaseAudioEvent_unlock(swigCPtr, this);
  }

  public boolean isLocked() {
    return MWEngineCoreJNI.BaseAudioEvent_isLocked(swigCPtr, this);
  }

  public float getVolume() {
    return MWEngineCoreJNI.BaseAudioEvent_getVolume(swigCPtr, this);
  }

  public void setVolume(float value) {
    MWEngineCoreJNI.BaseAudioEvent_setVolume(swigCPtr, this, value);
  }

}
