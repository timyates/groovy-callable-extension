# groovy-callable-extension

Experimental extension which makes all objects callable.

ie:

    String s = 'tim'
    assert s() == 'tim'

calling an Iterator would return the `next()` value

use-case (taken from [this groovy-user mailing list post](http://groovy.329449.n5.nabble.com/Would-it-be-nice-to-support-call-on-all-objects-td5710253.html)):

    class PowerManager { 
      def power( amps, volts ) { 
        amps() * volts() 
      } 

      def currentSource 
      def voltageSource 

      void update() { 
        println "Power drain is currently: ${power( currentSource, voltageSource )}" 
      } 
    } 

    // Mock readings from a volt meter
    def voltMeter = [ hasNext:{ true }, next:{ 10.0 + Math.random() } ] as Iterator

    def manager = new PowerManager( currentSource:5,
                                    voltageSource:{ voltMeter.hasNext() ? voltMeter.next() : 0 } ) 

    10.times { manager.update() } 
