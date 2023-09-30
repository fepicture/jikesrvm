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
package org.jikesrvm.mm.mmtk;

import static org.jikesrvm.runtime.UnboxedSizeConstants.LOG_BYTES_IN_ADDRESS;

import org.jikesrvm.VM;
import org.jikesrvm.mm.mminterface.Selected;
import org.jikesrvm.runtime.Magic;
import org.jikesrvm.util.Services;
import org.mmtk.plan.TraceLocal;
import org.mmtk.plan.ProtonProcessorTracer;
import org.mmtk.plan.Phase;
import org.vmmagic.pragma.NoInline;
import org.vmmagic.pragma.Uninterruptible;
import org.vmmagic.pragma.UninterruptibleNoWarn;
import org.vmmagic.pragma.Unpreemptible;
import org.vmmagic.pragma.UnpreemptibleNoWarn;
import org.vmmagic.unboxed.AddressArray;
import org.vmmagic.unboxed.ObjectReference;
import org.vmmagic.unboxed.Offset;
import org.vmmagic.unboxed.Word;
import static org.jikesrvm.runtime.SysCall.sysCall;

/**
 * This class manages the processing of proton objects.
 * <p>
 * TODO can this be a linked list?
 */
@Uninterruptible
public final class ProtonProcessor extends org.mmtk.vm.ProtonProcessor {

  /********************************************************************
   * Class fields
   */

  /** The ProtonProcessor singleton */
  private static final ProtonProcessor protonProcessor = new ProtonProcessor();

  public enum Phase {
    PREPARE, 
    SOFT_REFS,
    WEAK_REFS,
    FINALIZABLE,
    PHANTOM_REFS
  }

  private static Phase phaseId;

  /**
   * Create a new table.
   */
  protected ProtonProcessor() {
    phaseId = Phase.PREPARE;
  }

  /**
   * {@inheritDoc}.
   * <p>
   * Currently ignores the nursery hint.
   * <p>
   * TODO parallelise this code?
   *
   * @param trace   The trace
   * @param nursery Is this a nursery collection ?
   */


  @Override
  @UninterruptibleNoWarn
  public void forward(ProtonProcessorTracer trace, boolean isNursery) {  
    if (phaseId == Phase.PREPARE) {
      org.mmtk.vm.VM.finalizableProcessor.forward(trace, isNursery);
    }
  }

  @Override
  @UninterruptibleNoWarn
  public boolean scan(ProtonProcessorTracer trace, boolean isNursery, boolean needRetain) {
    if (phaseId == Phase.PREPARE) {
      phaseId = Phase.SOFT_REFS;
      // todo: may we do not need return?
      return true;
    } else if (phaseId == Phase.SOFT_REFS) {
      org.mmtk.vm.VM.softReferences.scan(trace, isNursery, needRetain);
      phaseId = Phase.WEAK_REFS;
      return true;
    } else if (phaseId == Phase.WEAK_REFS) {
          sysCall.hell_world();

      org.mmtk.vm.VM.weakReferences.scan(trace, isNursery, needRetain);
    sysCall.hell_world();

      phaseId = Phase.FINALIZABLE;
      return true;
    } else if (phaseId == Phase.FINALIZABLE) {
    sysCall.hell_world();
    org.mmtk.vm.VM.finalizableProcessor.scan(trace, isNursery);

      phaseId = Phase.PHANTOM_REFS;
      return true;
    } else if (phaseId == Phase.PHANTOM_REFS) {
      org.mmtk.vm.VM.phantomReferences.scan(trace, isNursery, needRetain);
      phaseId = Phase.PREPARE;
      return false;
    } else {
      // todo: throw exception
      return false;
    }
  }

  /***********************************************************************
   * Static methods.
   */

  /** @return the processor singleton */
  public static ProtonProcessor getProcessor() {
    return protonProcessor;
  }

}
