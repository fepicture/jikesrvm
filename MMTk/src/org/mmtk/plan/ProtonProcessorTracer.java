package org.mmtk.plan;

import org.mmtk.plan.TraceLocal;
import org.vmmagic.unboxed.ObjectReference;

public interface ProtonProcessorTracer {
  boolean isLive(ObjectReference object);
  ObjectReference getForwardedFinalizable(ObjectReference object);
  ObjectReference getForwardedReferent(ObjectReference object);
  ObjectReference getForwardedReference(ObjectReference object);
  ObjectReference retainReferent(ObjectReference object);
  ObjectReference retainForFinalize(ObjectReference object);
}
