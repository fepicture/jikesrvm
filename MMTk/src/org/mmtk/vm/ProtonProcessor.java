/*
 *  This file is part of the Jikes RVM project (http://jikesrvm.org).
 *
 *  This file is licensed to You under the Eclipse Public License (EPL);
 *  You may not use this file except in compliance with the License. You
 *  may obtain a copy of the License at
 *
 *      http://www.opensource.org/licenses/eclipse-1.0.php
 *
 *  See the COPYRIGHT.txt file distributed with this work for information
 *  regarding copyright ownership.
 */
package org.mmtk.vm;

import org.mmtk.plan.ProtonProcessorTracer;
import org.mmtk.plan.TraceLocal;
import org.vmmagic.pragma.Uninterruptible;
  //  fix me 
/*
 * repos/jikesrvm/MMTk/src/org/mmtk/vm/ProtonProcessor.java:18: error: package org.jikesrvm.mm.mmtk does not exist
    [javac] import org.jikesrvm.mm.mmtk.RustTraceLocal
 */

/**
 * This class manages Proton objects.
 */
@Uninterruptible
public abstract class ProtonProcessor {

  public abstract boolean scan(ProtonProcessorTracer trace, boolean isNursery, boolean needRetain);

  /**
   * Iterates over and forward entries in the table.
   *
   * @param trace the trace to use for the processing of the references
   * @param nursery if {@code true}, scan only references generated since
   *  last scan. Otherwise, scan all references.
   */
  public abstract void forward(ProtonProcessorTracer trace, boolean isNursery);
  // @Override fix me 
/*
 * repos/jikesrvm/MMTk/src/org/mmtk/vm/ProtonProcessor.java:18: error: package org.jikesrvm.mm.mmtk does not exist
    [javac] import org.jikesrvm.mm.mmtk.RustTraceLocal
 */

}
