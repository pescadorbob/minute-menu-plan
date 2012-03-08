dataSource {
    pooled = true
    driverClassName = "com.mysql.jdbc.Driver"
    dialect = org.hibernate.dialect.MySQL5InnoDBDialect // must be set for transactions to work!
    username = "root"
    password = "igdefault"
    properties {
        minIdle = 1
        numTestsPerEvictionRun = 3
        testOnBorrow = true
        testWhileIdle = true
        testOnReturn = true
        validationQuery = "SELECT 1"
        minEvictableIdleTimeMillis = (1000 * 60 * 5)
        timeBetweenEvictionRunsMillis = (1000 * 60 * 5)
    }

}
hibernate {
    cache.use_second_level_cache=true
    cache.use_query_cache=true
    cache.provider_class='net.sf.ehcache.hibernate.EhCacheProvider'
}
// environment specific settings
environments {
	developmentback {
		dataSource {
//			dbCreate = "create" // one of 'create', 'create-drop','update'
			loggingSql = true
            dbCreate = "update"
			url = "jdbc:mysql://localhost:3306/mmp-dev?autoReconnect=true"
		}
	}
//	developmentprodcopy {
	development {
		dataSource {
//			dbCreate = "create" // one of 'create', 'create-drop','update'
			loggingSql = true
			url = "jdbc:mysql://localhost:3306/master-prod-03082012?autoReconnect=true"
		}
	}
	developmentOrig {
		dataSource {
//			dbCreate = "create" // one of 'create', 'create-drop','update'
			loggingSql = true
			url = "jdbc:mysql://localhost:3306/mmp-prod-copy2?autoReconnect=true"
		}
	}
	test {
		dataSource {
//			dbCreate = "create"
			loggingSql = true
			url = "jdbc:mysql://localhost:3306/menuplanner_test?autoReconnect=true"
		}
	}
	qa {
		dataSource {
			url = "jdbc:mysql://localhost:3306/menuplanner_qa?autoReconnect=true"
		}
	}
	beta {
		dataSource {
			url = "jdbc:mysql://localhost:3306/menuplanner_beta?autoReconnect=true"
		}
	}
	production {
		dataSource {
			username = "root"
			password = "mmdefault"
			url = "jdbc:mysql://localhost:3306/menuplanner?autoReconnect=true"
		}
	}
}
