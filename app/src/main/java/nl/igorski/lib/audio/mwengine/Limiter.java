/* ----------------------------------------------------------------------------
 * This file was automatically generated by SWIG (http://www.swig.org).
 * Version 3.0.12
 *
 * Do not make changes to this file unless you know what you are doing--modify
 * the SWIG interface file instead.
 * ----------------------------------------------------------------------------- */

package nl.igorski.lib.audio.mwengine;

public class Limiter extends BaseProcessor {
  private transient long swigCPtr;

  protected Limiter(long cPtr, boolean cMemoryOwn) {
    super(MWEngineCoreJNI.Limiter_SWIGUpcast(cPtr), cMemoryOwn);
    swigCPtr = cPtr;
  }

  protected static long getCPtr(Limiter obj) {
    return (obj == null) ? 0 : obj.swigCPtr;
  }

  protected void finalize() {
    delete();
  }

  public synchronized void delete() {
    if (swigCPtr != 0) {
      if (swigCMemOwn) {
        swigCMemOwn = false;
        MWEngineCoreJNI.delete_Limiter(swigCPtr);
      }
      swigCPtr = 0;
    }
    super.delete();
  }

  public Limiter() {
    this(MWEngineCoreJNI.new_Limiter__SWIG_0(), true);
  }

  public Limiter(float attackMs, float releaseMs, int sampleRate, int amountOfChannels) {
    this(MWEngineCoreJNI.new_Limiter__SWIG_1(attackMs, releaseMs, sampleRate, amountOfChannels), true);
  }

  public float getLinearGR() {
    return MWEngineCoreJNI.Limiter_getLinearGR(swigCPtr, this);
  }

  public void process(SWIGTYPE_p_AudioBuffer sampleBuffer, boolean isMonoSource) {
    MWEngineCoreJNI.Limiter_process(swigCPtr, this, SWIGTYPE_p_AudioBuffer.getCPtr(sampleBuffer), isMonoSource);
  }

  public boolean isCacheable() {
    return MWEngineCoreJNI.Limiter_isCacheable(swigCPtr, this);
  }

}
