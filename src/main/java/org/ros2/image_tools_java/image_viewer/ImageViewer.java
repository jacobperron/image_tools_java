/* Copyright 2020 Open Source Robotics Foundation, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.ros2.image_tools_java.image_viewer;

import org.ros2.rcljava.RCLJava;
import org.ros2.rcljava.node.BaseComposableNode;
import org.ros2.rcljava.node.Node;
import org.ros2.rcljava.subscription.Subscription;
import org.ros2.rcljava.Time;

public class ImageViewer extends BaseComposableNode {
  private Subscription<sensor_msgs.msg.Image> subscription;

  public ImageViewer() {
    super("image_viewer");
    subscription = node.<sensor_msgs.msg.Image>createSubscription(
        sensor_msgs.msg.Image.class, "image", this ::imageCallback);
  }

  private void imageCallback(final sensor_msgs.msg.Image msg) {
    Node node = this.getNode();
    Time time_sent = new Time(msg.getHeader().getStamp(), node.getClock().getClockType());
    Time time_now = node.getClock().now();
    double diff_seconds = (time_now.nanoseconds() - time_sent.nanoseconds()) / 1000000000.0;
    System.out.printf("Image received. Time delta (s): %.2f\n", diff_seconds);
  }

  public static void main(final String[] args) throws InterruptedException, Exception {
    RCLJava.rclJavaInit();
    RCLJava.spin(new ImageViewer());
  }
}
