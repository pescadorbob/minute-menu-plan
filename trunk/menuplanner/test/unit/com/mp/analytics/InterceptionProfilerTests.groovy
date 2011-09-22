package com.mp.analytics

import org.codehaus.groovy.runtime.StringBufferWriter
import java.sql.PreparedStatement

class InterceptionProfilerTests extends GroovyTestCase {
    protected void setUp() {
        super.setUp()
    }

    protected void tearDown() {
        super.tearDown()
    }

    void testSomething() {
      def log = new StringBuffer("\n")
      def tracer = new TracingInterceptor();
      tracer.writer = new StringBufferWriter(log)
      def proxy = ProxyMetaClass.getInstance(PreparedStatement.class)
      proxy.interceptor = tracer
      proxy.use {
        assert 1 == new Whatever().outer()
      }

      assert log.toString() == """
before com.mp.analytics.Whatever.ctor()
after  com.mp.analytics.Whatever.ctor()
before com.mp.analytics.Whatever.outer()
 before com.mp.analytics.Whatever.inner()
 after  com.mp.analytics.Whatever.inner()
after  com.mp.analytics.Whatever.outer()
"""
    }
}
