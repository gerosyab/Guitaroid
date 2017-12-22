/* ----------------------------------------------------------------------------
 * This file was automatically generated by SWIG (http://www.swig.org).
 * Version 3.0.12
 *
 * Do not make changes to this file unless you know what you are doing--modify
 * the SWIG interface file instead.
 * ----------------------------------------------------------------------------- */

package nl.igorski.lib.audio.mwengine;

public class SampleManager {
  private transient long swigCPtr;
  protected transient boolean swigCMemOwn;

  protected SampleManager(long cPtr, boolean cMemoryOwn) {
    swigCMemOwn = cMemoryOwn;
    swigCPtr = cPtr;
  }

  protected static long getCPtr(SampleManager obj) {
    return (obj == null) ? 0 : obj.swigCPtr;
  }

  protected void finalize() {
    delete();
  }

  public synchronized void delete() {
    if (swigCPtr != 0) {
      if (swigCMemOwn) {
        swigCMemOwn = false;
        MWEngineCoreJNI.delete_SampleManager(swigCPtr);
      }
      swigCPtr = 0;
    }
  }

  public static void setSample(String aKey, SWIGTYPE_p_AudioBuffer aBuffer) {
    MWEngineCoreJNI.SampleManager_setSample(aKey, SWIGTYPE_p_AudioBuffer.getCPtr(aBuffer));
  }

  public static SWIGTYPE_p_AudioBuffer getSample(String aIdentifier) {
    long cPtr = MWEngineCoreJNI.SampleManager_getSample(aIdentifier);
    return (cPtr == 0) ? null : new SWIGTYPE_p_AudioBuffer(cPtr, false);
  }

  public static int getSampleLength(String aIdentifier) {
    return MWEngineCoreJNI.SampleManager_getSampleLength(aIdentifier);
  }

  public static boolean hasSample(String aIdentifier) {
    return MWEngineCoreJNI.SampleManager_hasSample(aIdentifier);
  }

  public static void removeSample(String aIdentifier) {
    MWEngineCoreJNI.SampleManager_removeSample(aIdentifier);
  }

  public static void flushSamples() {
    MWEngineCoreJNI.SampleManager_flushSamples();
  }

  public SampleManager() {
    this(MWEngineCoreJNI.new_SampleManager(), true);
  }

}
