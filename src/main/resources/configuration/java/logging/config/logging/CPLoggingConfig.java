package com.cleverpine.{applicationName}.config.logging;

import com.cleverpine.springlogginglibrary.aop.EnableCPLogging;
import org.springframework.context.annotation.ComponentScan;

@EnableCPLogging
@ComponentScan(basePackages = { "com.cleverpine.springlogginglibrary.*", "com.cleverpine.{applicationName}" })
public class CPLoggingConfig {

}
