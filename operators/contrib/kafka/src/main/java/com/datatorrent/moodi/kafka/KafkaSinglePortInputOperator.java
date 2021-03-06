/*
 * Copyright (c) 2012-2017 DataTorrent, Inc.
 * All Rights Reserved.
 * The use of this source code is governed by the Limited License located at
 * https://www.datatorrent.com/datatorrent-openview-software-license/
 */

package com.datatorrent.moodi.kafka;

import org.apache.kafka.clients.consumer.ConsumerRecord;

import com.datatorrent.api.AutoMetric;

@org.apache.hadoop.classification.InterfaceStability.Evolving
public class KafkaSinglePortInputOperator extends org.apache.apex.malhar.kafka.KafkaSinglePortInputOperator
{

  /**
   * No. of messages emitted in the current window.
   */
  @AutoMetric
  private transient long numMessages;

  /**
   * No. of bytes emitted in the current window.
   */
  @AutoMetric
  private transient long numBytes;

  /**
   * Total no. of bytes emitted till now.
   */
  @AutoMetric
  private long totalBytes;

  @Override
  public void beginWindow(long wid)
  {
    super.beginWindow(wid);
    numMessages = 0;
    numBytes = 0;
  }

  @Override
  protected void emitTuple(String cluster, ConsumerRecord<byte[], byte[]> message)
  {
    super.emitTuple(cluster, message);
    ++numMessages;
    numBytes += message.value().length;
  }

  @Override
  public void endWindow()
  {
    super.endWindow();
    totalBytes += numBytes;
  }
}
