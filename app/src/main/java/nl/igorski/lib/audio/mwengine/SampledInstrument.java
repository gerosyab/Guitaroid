/* ----------------------------------------------------------------------------
 * This file was automatically generated by SWIG (http://www.swig.org).
 * Version 3.0.12
 *
 * Do not make changes to this file unless you know what you are doing--modify
 * the SWIG interface file instead.
 * ----------------------------------------------------------------------------- */

package nl.igorski.lib.audio.mwengine;

public class SampledInstrument extends BaseInstrument {
  private transient long swigCPtr;

  protected SampledInstrument(long cPtr, boolean cMemoryOwn) {
    super(MWEngineCoreJNI.SampledInstrument_SWIGUpcast(cPtr), cMemoryOwn);
    swigCPtr = cPtr;
  }

  protected static long getCPtr(SampledInstrument obj) {
    return (obj == null) ? 0 : obj.swigCPtr;
  }

  protected void finalize() {
    delete();
  }

  public synchronized void delete() {
    if (swigCPtr != 0) {
      if (swigCMemOwn) {
        swigCMemOwn = false;
        MWEngineCoreJNI.delete_SampledInstrument(swigCPtr);
      }
      swigCPtr = 0;
    }
    super.delete();
  }

  public SampledInstrument() {
    this(MWEngineCoreJNI.new_SampledInstrument(), true);
  }

  public boolean removeEvent(BaseAudioEvent audioEvent, boolean isLiveEvent) {
    return MWEngineCoreJNI.SampledInstrument_removeEvent(swigCPtr, this, BaseAudioEvent.getCPtr(audioEvent), audioEvent, isLiveEvent);
  }

  public void updateEvents() {
    MWEngineCoreJNI.SampledInstrument_updateEvents(swigCPtr, this);
  }

}
