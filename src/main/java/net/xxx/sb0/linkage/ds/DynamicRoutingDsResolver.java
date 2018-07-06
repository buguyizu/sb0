package net.xxx.sb0.linkage.ds;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

public class DynamicRoutingDsResolver extends AbstractRoutingDataSource {

  @Override
  protected Object determineCurrentLookupKey() {
    return ConTarget.get();
  }
}
