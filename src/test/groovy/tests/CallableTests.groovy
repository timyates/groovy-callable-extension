/*
 * Copyright 2013 the original author or authors.
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

package tests

import spock.lang.Specification

public class CallableTests extends Specification {
  def 'Integer test'() {
    when:
      def input = 3
      def rslt  = input()
    then:
      rslt == input
  }

  def 'String test'() {
    when:
      def input = 'tim'
      def rslt  = input()
    then:
      rslt == input
  }

  def 'List test'() {
    when:
      def input = [1,2,3]
      def rslt  = input()
    then:
      rslt == input
  }

  def 'Iterator test'() {
    when:
      def x     = 10
      def input = [ hasNext:{ true }, next:{ x++ } ] as Iterator
      def rslt1 = input()
      def rslt2 = input()
    then:
      rslt1 == 10
      rslt2 == 11
  }

  def 'test from the README'() {
    setup:
      // Mock readings from a volt meter
      def voltMeter = [ hasNext:{ true }, next:{ 1 } ] as Iterator
      def manager = new PowerManager( currentSource:5,
                                      voltageSource:{ voltMeter.hasNext() ? voltMeter.next() : 0 } ) 
    when:
      def rslt = manager.update()

    then:
      rslt == "Power drain is currently: 5" 
  }
}

class PowerManager { 
  def power( amps, volts ) { 
    amps() * volts() 
  } 

  def currentSource 
  def voltageSource 

  String update() { 
    "Power drain is currently: ${power( currentSource, voltageSource )}" 
  } 
} 