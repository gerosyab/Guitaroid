/* ----------------------------------------------------------------------------
 * This file was automatically generated by SWIG (http://www.swig.org).
 * Version 3.0.12
 *
 * Do not make changes to this file unless you know what you are doing--modify
 * the SWIG interface file instead.
 * ----------------------------------------------------------------------------- */

package nl.igorski.lib.audio.mwengine;

public class BaseCacheableAudioEvent extends BaseAudioEvent {
  private transient long swigCPtr;

  protected BaseCacheableAudioEvent(long cPtr, boolean cMemoryOwn) {
    super(MWEngineCoreJNI.BaseCacheableAudioEvent_SWIGUpcast(cPtr), cMemoryOwn);
    swigCPtr = cPtr;
  }

  protected static long getCPtr(BaseCacheableAudioEvent obj) {
    return (obj == null) ? 0 : obj.swigCPtr;
  }

  protected void finalize() {
    delete();
  }

  public synchronized void delete() {
    if (swigCPtr != 0) {
      if (swigCMemOwn) {
        swigCMemOwn = false;
        MWEngineCoreJNI.delete_BaseCacheableAudioEvent(swigCPtr);
      }
      swigCPtr = 0;
    }
    super.delete();
  }

  public BaseCacheableAudioEvent(BaseInstrument instrument) {
    this(MWEngineCoreJNI.new_BaseCacheableAudioEvent(BaseInstrument.getCPtr(instrument), instrument), true);
  }

  public void setAutoCache(boolean value) {
    MWEngineCoreJNI.BaseCacheableAudioEvent_setAutoCache(swigCPtr, this, value);
  }

  public void cache(boolean doCallback) {
    MWEngineCoreJNI.BaseCacheableAudioEvent_cache(swigCPtr, this, doCallback);
  }

  public boolean isCached() {
    return MWEngineCoreJNI.BaseCacheableAudioEvent_isCached(swigCPtr, this);
  }

  public void setBulkCacheable(boolean value) {
    MWEngineCoreJNI.BaseCacheableAudioEvent_setBulkCacheable(swigCPtr, this, value);
  }

}
