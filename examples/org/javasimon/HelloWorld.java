package org.javasimon;

/**
 * HelloWorld is the most basic example of Stopwatch usage. You can show this
 * even to managers - it's that easy. :-)
 *
 * @author <a href="mailto:richard.richter@siemens.sk">Richard "Virgo" Richter</a>
 * @version $Revision$ $Date$
 * @created 7.8.2008 13:19:30
 * @since 1.0
 */
public final class HelloWorld {
	private HelloWorld() {
	}

	public static void main(String[] args) {
		SimonStopwatch stopwatch = SimonFactory.getStopwatch("org.javasimon.HelloWorld-stopwatch");

		stopwatch.start();
		System.out.println("Hello world, " + stopwatch);
		stopwatch.stop();

		System.out.println("Result: " + stopwatch);
	}
}
