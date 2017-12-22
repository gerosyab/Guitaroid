/* ----------------------------------------------------------------------------
 * This file was automatically generated by SWIG (http://www.swig.org).
 * Version 3.0.12
 *
 * Do not make changes to this file unless you know what you are doing--modify
 * the SWIG interface file instead.
 * ----------------------------------------------------------------------------- */

package nl.igorski.lib.audio.mwengine;

public class BaseInstrument {
  private transient long swigCPtr;
  protected transient boolean swigCMemOwn;

  protected BaseInstrument(long cPtr, boolean cMemoryOwn) {
    swigCMemOwn = cMemoryOwn;
    swigCPtr = cPtr;
  }

  protected static long getCPtr(BaseInstrument obj) {
    return (obj == null) ? 0 : obj.swigCPtr;
  }

  protected void finalize() {
    delete();
  }

  public synchronized void delete() {
    if (swigCPtr != 0) {
      if (swigCMemOwn) {
        swigCMemOwn = false;
        MWEngineCoreJNI.delete_BaseInstrument(swigCPtr);
      }
      swigCPtr = 0;
    }
  }

  public BaseInstrument() {
    this(MWEngineCoreJNI.new_BaseInstrument(), true);
  }

  public boolean hasEvents() {
    return MWEngineCoreJNI.BaseInstrument_hasEvents(swigCPtr, this);
  }

  public boolean hasLiveEvents() {
    return MWEngineCoreJNI.BaseInstrument_hasLiveEvents(swigCPtr, this);
  }

  public void updateEvents() {
    MWEngineCoreJNI.BaseInstrument_updateEvents(swigCPtr, this);
  }

  public SWIGTYPE_p_std__vectorT_BaseAudioEvent_p_t getEvents() {
    long cPtr = MWEngineCoreJNI.BaseInstrument_getEvents(swigCPtr, this);
    return (cPtr == 0) ? null : new SWIGTYPE_p_std__vectorT_BaseAudioEvent_p_t(cPtr, false);
  }

  public SWIGTYPE_p_std__vectorT_BaseAudioEvent_p_t getLiveEvents() {
    long cPtr = MWEngineCoreJNI.BaseInstrument_getLiveEvents(swigCPtr, this);
    return (cPtr == 0) ? null : new SWIGTYPE_p_std__vectorT_BaseAudioEvent_p_t(cPtr, false);
  }

  public void clearEvents() {
    MWEngineCoreJNI.BaseInstrument_clearEvents(swigCPtr, this);
  }

  public boolean removeEvent(BaseAudioEvent audioEvent, boolean isLiveEvent) {
    return MWEngineCoreJNI.BaseInstrument_removeEvent(swigCPtr, this, BaseAudioEvent.getCPtr(audioEvent), audioEvent, isLiveEvent);
  }

  public void registerInSequencer() {
    MWEngineCoreJNI.BaseInstrument_registerInSequencer(swigCPtr, this);
  }

  public void unregisterFromSequencer() {
    MWEngineCoreJNI.BaseInstrument_unregisterFromSequencer(swigCPtr, this);
  }

  public void setAudioChannel(AudioChannel value) {
    MWEngineCoreJNI.BaseInstrument_audioChannel_set(swigCPtr, this, AudioChannel.getCPtr(value), value);
  }

  public AudioChannel getAudioChannel() {
    long cPtr = MWEngineCoreJNI.BaseInstrument_audioChannel_get(swigCPtr, this);
    return (cPtr == 0) ? null : new AudioChannel(cPtr, false);
  }

  public void setIndex(int value) {
    MWEngineCoreJNI.BaseInstrument_index_set(swigCPtr, this, value);
  }

  public int getIndex() {
    return MWEngineCoreJNI.BaseInstrument_index_get(swigCPtr, this);
  }

}
