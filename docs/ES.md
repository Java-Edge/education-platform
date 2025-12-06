javaedge@JavaEdgedeMac-Studio bin % ./elasticsearch
[2025-11-28T13:25:30,439][INFO ][o.e.b.Elasticsearch      ] [JavaEdgedeMac-Studio.local] version[9.2.1], pid[80012], build[tar/4ad0ef0e98a2e72fafbd79a19fa5cae2f026117d/2025-11-06T22:07:39.673130621Z], OS[Mac OS X/15.6.1/aarch64], JVM[Oracle Corporation/OpenJDK 64-Bit Server VM/25.0.1/25.0.1+8-27]
[2025-11-28T13:25:30,442][INFO ][o.e.b.Elasticsearch      ] [JavaEdgedeMac-Studio.local] JVM home [/Users/javaedge/soft/elasticsearch-9.2.1/jdk.app/Contents/Home], using bundled JDK [true]
[2025-11-28T13:25:30,443][INFO ][o.e.b.Elasticsearch      ] [JavaEdgedeMac-Studio.local] JVM arguments [-Des.networkaddress.cache.ttl=60, -Des.networkaddress.cache.negative.ttl=10, -XX:+AlwaysPreTouch, -Xss1m, -Djava.awt.headless=true, -Dfile.encoding=UTF-8, -Djna.nosys=true, -XX:-OmitStackTraceInFastThrow, -Dio.netty.noUnsafe=true, -Dio.netty.noKeySetOptimization=true, -Dio.netty.recycler.maxCapacityPerThread=0, --add-opens=org.apache.lucene.core/org.apache.lucene.codecs.lucene99=org.elasticsearch.server, --add-opens=org.apache.lucene.core/org.apache.lucene.internal.vectorization=org.elasticsearch.server, -Dlog4j.shutdownHookEnabled=false, -Dlog4j2.disable.jmx=true, -Dlog4j2.formatMsgNoLookups=true, -Djava.locale.providers=CLDR, -Dorg.apache.lucene.vectorization.upperJavaFeatureVersion=25, -Des.path.home=/Users/javaedge/soft/elasticsearch-9.2.1, -Des.distribution.type=tar, -Des.java.type=bundled JDK, --enable-native-access=org.elasticsearch.nativeaccess,org.apache.lucene.core, --enable-native-access=ALL-UNNAMED, --illegal-native-access=deny, -XX:ReplayDataFile=logs/replay_pid%p.log, -XX:+EnableDynamicAgentLoading, -Djdk.attach.allowAttachSelf=true, --patch-module=java.base=/Users/javaedge/soft/elasticsearch-9.2.1/lib/entitlement-bridge/elasticsearch-entitlement-bridge-9.2.1.jar, --add-exports=java.base/org.elasticsearch.entitlement.bridge=org.elasticsearch.entitlement,java.logging,java.net.http,java.naming,jdk.net, -XX:+UseG1GC, -Djava.io.tmpdir=/var/folders/nm/yvdpqzbd5v7gxd_dc90fq6mr0000gn/T/elasticsearch-16561842064718052507, --add-modules=jdk.incubator.vector, -Dorg.apache.lucene.store.defaultReadAdvice=normal, -Dorg.apache.lucene.store.MMapDirectory.sharedArenaMaxPermits=1, -XX:+HeapDumpOnOutOfMemoryError, -XX:+ExitOnOutOfMemoryError, -XX:ErrorFile=hs_err_pid%p.log, -Xlog:gc*,gc+age=trace,safepoint:file=gc.log:utctime,level,pid,tags:filecount=32,filesize=64m, -Xms31744m, -Xmx31744m, -XX:MaxDirectMemorySize=16642998272, -XX:InitiatingHeapOccupancyPercent=30, -XX:G1ReservePercent=25, --module-path=/Users/javaedge/soft/elasticsearch-9.2.1/lib, --add-modules=jdk.net, --add-modules=jdk.management.agent, --add-modules=ALL-MODULE-PATH, -Djdk.module.main=org.elasticsearch.server]
[2025-11-28T13:25:30,443][INFO ][o.e.b.Elasticsearch      ] [JavaEdgedeMac-Studio.local] Default Locale [zh_CN_#Hans]
[2025-11-28T13:25:30,499][INFO ][o.e.n.j.JdkVectorLibrary ] [JavaEdgedeMac-Studio.local] vec_caps=1
[2025-11-28T13:25:30,501][INFO ][o.e.n.NativeAccess       ] [JavaEdgedeMac-Studio.local] Using native vector library; to disable start with -Dorg.elasticsearch.nativeaccess.enableVectorLibrary=false
[2025-11-28T13:25:30,504][INFO ][o.e.n.NativeAccess       ] [JavaEdgedeMac-Studio.local] Using [jdk] native provider and native methods for [MacOS]
[2025-11-28T13:25:30,538][INFO ][o.a.l.i.v.PanamaVectorizationProvider] [JavaEdgedeMac-Studio.local] Java vector incubator API enabled; uses preferredBitSize=128
[2025-11-28T13:25:30,559][INFO ][o.e.b.Elasticsearch      ] [JavaEdgedeMac-Studio.local] Bootstrapping Entitlements
[2025-11-28T13:25:31,865][WARN ][o.e.x.g.GPUSupport       ] [JavaEdgedeMac-Studio.local] GPU based vector indexing is not supported on this platform; cuvs-java supports only Linux; cuvs-java supports only x86
[2025-11-28T13:25:31,943][INFO ][o.e.p.PluginsService     ] [JavaEdgedeMac-Studio.local] loaded module [repository-url]
[2025-11-28T13:25:31,943][INFO ][o.e.p.PluginsService     ] [JavaEdgedeMac-Studio.local] loaded module [rest-root]
[2025-11-28T13:25:31,943][INFO ][o.e.p.PluginsService     ] [JavaEdgedeMac-Studio.local] loaded module [x-pack-core]
[2025-11-28T13:25:31,943][INFO ][o.e.p.PluginsService     ] [JavaEdgedeMac-Studio.local] loaded module [x-pack-redact]
[2025-11-28T13:25:31,943][INFO ][o.e.p.PluginsService     ] [JavaEdgedeMac-Studio.local] loaded module [ingest-user-agent]
[2025-11-28T13:25:31,943][INFO ][o.e.p.PluginsService     ] [JavaEdgedeMac-Studio.local] loaded module [x-pack-async-search]
[2025-11-28T13:25:31,943][INFO ][o.e.p.PluginsService     ] [JavaEdgedeMac-Studio.local] loaded module [x-pack-monitoring]
[2025-11-28T13:25:31,943][INFO ][o.e.p.PluginsService     ] [JavaEdgedeMac-Studio.local] loaded module [repository-s3]
[2025-11-28T13:25:31,943][INFO ][o.e.p.PluginsService     ] [JavaEdgedeMac-Studio.local] loaded module [x-pack-esql-core]
[2025-11-28T13:25:31,944][INFO ][o.e.p.PluginsService     ] [JavaEdgedeMac-Studio.local] loaded module [x-pack-analytics]
[2025-11-28T13:25:31,944][INFO ][o.e.p.PluginsService     ] [JavaEdgedeMac-Studio.local] loaded module [search-business-rules]
[2025-11-28T13:25:31,944][INFO ][o.e.p.PluginsService     ] [JavaEdgedeMac-Studio.local] loaded module [x-pack-ent-search]
[2025-11-28T13:25:31,944][INFO ][o.e.p.PluginsService     ] [JavaEdgedeMac-Studio.local] loaded module [x-pack-autoscaling]
[2025-11-28T13:25:31,944][INFO ][o.e.p.PluginsService     ] [JavaEdgedeMac-Studio.local] loaded module [lang-painless]
[2025-11-28T13:25:31,944][INFO ][o.e.p.PluginsService     ] [JavaEdgedeMac-Studio.local] loaded module [x-pack-ml]
[2025-11-28T13:25:31,944][INFO ][o.e.p.PluginsService     ] [JavaEdgedeMac-Studio.local] loaded module [legacy-geo]
[2025-11-28T13:25:31,944][INFO ][o.e.p.PluginsService     ] [JavaEdgedeMac-Studio.local] loaded module [lang-mustache]
[2025-11-28T13:25:31,944][INFO ][o.e.p.PluginsService     ] [JavaEdgedeMac-Studio.local] loaded module [x-pack-ql]
[2025-11-28T13:25:31,944][INFO ][o.e.p.PluginsService     ] [JavaEdgedeMac-Studio.local] loaded module [logsdb]
[2025-11-28T13:25:31,944][INFO ][o.e.p.PluginsService     ] [JavaEdgedeMac-Studio.local] loaded module [rank-rrf]
[2025-11-28T13:25:31,944][INFO ][o.e.p.PluginsService     ] [JavaEdgedeMac-Studio.local] loaded module [analysis-common]
[2025-11-28T13:25:31,944][INFO ][o.e.p.PluginsService     ] [JavaEdgedeMac-Studio.local] loaded module [health-shards-availability]
[2025-11-28T13:25:31,945][INFO ][o.e.p.PluginsService     ] [JavaEdgedeMac-Studio.local] loaded module [transport-netty4]
[2025-11-28T13:25:31,945][INFO ][o.e.p.PluginsService     ] [JavaEdgedeMac-Studio.local] loaded module [aggregations]
[2025-11-28T13:25:31,945][INFO ][o.e.p.PluginsService     ] [JavaEdgedeMac-Studio.local] loaded module [ingest-common]
[2025-11-28T13:25:31,945][INFO ][o.e.p.PluginsService     ] [JavaEdgedeMac-Studio.local] loaded module [x-pack-identity-provider]
[2025-11-28T13:25:31,945][INFO ][o.e.p.PluginsService     ] [JavaEdgedeMac-Studio.local] loaded module [frozen-indices]
[2025-11-28T13:25:31,945][INFO ][o.e.p.PluginsService     ] [JavaEdgedeMac-Studio.local] loaded module [x-pack-text-structure]
[2025-11-28T13:25:31,945][INFO ][o.e.p.PluginsService     ] [JavaEdgedeMac-Studio.local] loaded module [x-pack-shutdown]
[2025-11-28T13:25:31,945][INFO ][o.e.p.PluginsService     ] [JavaEdgedeMac-Studio.local] loaded module [snapshot-repo-test-kit]
[2025-11-28T13:25:31,945][INFO ][o.e.p.PluginsService     ] [JavaEdgedeMac-Studio.local] loaded module [ml-package-loader]
[2025-11-28T13:25:31,945][INFO ][o.e.p.PluginsService     ] [JavaEdgedeMac-Studio.local] loaded module [kibana]
[2025-11-28T13:25:31,945][INFO ][o.e.p.PluginsService     ] [JavaEdgedeMac-Studio.local] loaded module [constant-keyword]
[2025-11-28T13:25:31,945][INFO ][o.e.p.PluginsService     ] [JavaEdgedeMac-Studio.local] loaded module [x-pack-logstash]
[2025-11-28T13:25:31,945][INFO ][o.e.p.PluginsService     ] [JavaEdgedeMac-Studio.local] loaded module [x-pack-graph]
[2025-11-28T13:25:31,946][INFO ][o.e.p.PluginsService     ] [JavaEdgedeMac-Studio.local] loaded module [x-pack-ccr]
[2025-11-28T13:25:31,946][INFO ][o.e.p.PluginsService     ] [JavaEdgedeMac-Studio.local] loaded module [rank-vectors]
[2025-11-28T13:25:31,946][INFO ][o.e.p.PluginsService     ] [JavaEdgedeMac-Studio.local] loaded module [x-pack-esql]
[2025-11-28T13:25:31,946][INFO ][o.e.p.PluginsService     ] [JavaEdgedeMac-Studio.local] loaded module [parent-join]
[2025-11-28T13:25:31,946][INFO ][o.e.p.PluginsService     ] [JavaEdgedeMac-Studio.local] loaded module [counted-keyword]
[2025-11-28T13:25:31,946][INFO ][o.e.p.PluginsService     ] [JavaEdgedeMac-Studio.local] loaded module [x-pack-enrich]
[2025-11-28T13:25:31,946][INFO ][o.e.p.PluginsService     ] [JavaEdgedeMac-Studio.local] loaded module [repositories-metering-api]
[2025-11-28T13:25:31,946][INFO ][o.e.p.PluginsService     ] [JavaEdgedeMac-Studio.local] loaded module [transform]
[2025-11-28T13:25:31,946][INFO ][o.e.p.PluginsService     ] [JavaEdgedeMac-Studio.local] loaded module [repository-azure]
[2025-11-28T13:25:31,946][INFO ][o.e.p.PluginsService     ] [JavaEdgedeMac-Studio.local] loaded module [dot-prefix-validation]
[2025-11-28T13:25:31,946][INFO ][o.e.p.PluginsService     ] [JavaEdgedeMac-Studio.local] loaded module [repository-gcs]
[2025-11-28T13:25:31,946][INFO ][o.e.p.PluginsService     ] [JavaEdgedeMac-Studio.local] loaded module [spatial]
[2025-11-28T13:25:31,946][INFO ][o.e.p.PluginsService     ] [JavaEdgedeMac-Studio.local] loaded module [x-pack-otel-data]
[2025-11-28T13:25:31,946][INFO ][o.e.p.PluginsService     ] [JavaEdgedeMac-Studio.local] loaded module [apm]
[2025-11-28T13:25:31,947][INFO ][o.e.p.PluginsService     ] [JavaEdgedeMac-Studio.local] loaded module [mapper-extras]
[2025-11-28T13:25:31,947][INFO ][o.e.p.PluginsService     ] [JavaEdgedeMac-Studio.local] loaded module [mapper-version]
[2025-11-28T13:25:31,947][INFO ][o.e.p.PluginsService     ] [JavaEdgedeMac-Studio.local] loaded module [x-pack-rollup]
[2025-11-28T13:25:31,947][INFO ][o.e.p.PluginsService     ] [JavaEdgedeMac-Studio.local] loaded module [percolator]
[2025-11-28T13:25:31,947][INFO ][o.e.p.PluginsService     ] [JavaEdgedeMac-Studio.local] loaded module [x-pack-migrate]
[2025-11-28T13:25:31,947][INFO ][o.e.p.PluginsService     ] [JavaEdgedeMac-Studio.local] loaded module [data-streams]
[2025-11-28T13:25:31,947][INFO ][o.e.p.PluginsService     ] [JavaEdgedeMac-Studio.local] loaded module [x-pack-stack]
[2025-11-28T13:25:31,947][INFO ][o.e.p.PluginsService     ] [JavaEdgedeMac-Studio.local] loaded module [rank-eval]
[2025-11-28T13:25:31,947][INFO ][o.e.p.PluginsService     ] [JavaEdgedeMac-Studio.local] loaded module [reindex]
[2025-11-28T13:25:31,947][INFO ][o.e.p.PluginsService     ] [JavaEdgedeMac-Studio.local] loaded module [streams]
[2025-11-28T13:25:31,947][INFO ][o.e.p.PluginsService     ] [JavaEdgedeMac-Studio.local] loaded module [x-pack-security]
[2025-11-28T13:25:31,947][INFO ][o.e.p.PluginsService     ] [JavaEdgedeMac-Studio.local] loaded module [blob-cache]
[2025-11-28T13:25:31,947][INFO ][o.e.p.PluginsService     ] [JavaEdgedeMac-Studio.local] loaded module [searchable-snapshots]
[2025-11-28T13:25:31,947][INFO ][o.e.p.PluginsService     ] [JavaEdgedeMac-Studio.local] loaded module [gpu]
[2025-11-28T13:25:31,947][INFO ][o.e.p.PluginsService     ] [JavaEdgedeMac-Studio.local] loaded module [x-pack-slm]
[2025-11-28T13:25:31,947][INFO ][o.e.p.PluginsService     ] [JavaEdgedeMac-Studio.local] loaded module [x-pack-geoip-enterprise-downloader]
[2025-11-28T13:25:31,947][INFO ][o.e.p.PluginsService     ] [JavaEdgedeMac-Studio.local] loaded module [snapshot-based-recoveries]
[2025-11-28T13:25:31,948][INFO ][o.e.p.PluginsService     ] [JavaEdgedeMac-Studio.local] loaded module [x-pack-watcher]
[2025-11-28T13:25:31,948][INFO ][o.e.p.PluginsService     ] [JavaEdgedeMac-Studio.local] loaded module [old-lucene-versions]
[2025-11-28T13:25:31,948][INFO ][o.e.p.PluginsService     ] [JavaEdgedeMac-Studio.local] loaded module [x-pack-ilm]
[2025-11-28T13:25:31,948][INFO ][o.e.p.PluginsService     ] [JavaEdgedeMac-Studio.local] loaded module [x-pack-inference]
[2025-11-28T13:25:31,948][INFO ][o.e.p.PluginsService     ] [JavaEdgedeMac-Studio.local] loaded module [x-pack-voting-only-node]
[2025-11-28T13:25:31,948][INFO ][o.e.p.PluginsService     ] [JavaEdgedeMac-Studio.local] loaded module [x-pack-deprecation]
[2025-11-28T13:25:31,948][INFO ][o.e.p.PluginsService     ] [JavaEdgedeMac-Studio.local] loaded module [x-pack-fleet]
[2025-11-28T13:25:31,948][INFO ][o.e.p.PluginsService     ] [JavaEdgedeMac-Studio.local] loaded module [x-pack-profiling]
[2025-11-28T13:25:31,948][INFO ][o.e.p.PluginsService     ] [JavaEdgedeMac-Studio.local] loaded module [x-pack-aggregate-metric]
[2025-11-28T13:25:31,948][INFO ][o.e.p.PluginsService     ] [JavaEdgedeMac-Studio.local] loaded module [x-pack-downsample]
[2025-11-28T13:25:31,948][INFO ][o.e.p.PluginsService     ] [JavaEdgedeMac-Studio.local] loaded module [ingest-geoip]
[2025-11-28T13:25:31,948][INFO ][o.e.p.PluginsService     ] [JavaEdgedeMac-Studio.local] loaded module [x-pack-write-load-forecaster]
[2025-11-28T13:25:31,948][INFO ][o.e.p.PluginsService     ] [JavaEdgedeMac-Studio.local] loaded module [exponential-histogram]
[2025-11-28T13:25:31,948][INFO ][o.e.p.PluginsService     ] [JavaEdgedeMac-Studio.local] loaded module [wildcard]
[2025-11-28T13:25:31,948][INFO ][o.e.p.PluginsService     ] [JavaEdgedeMac-Studio.local] loaded module [ingest-attachment]
[2025-11-28T13:25:31,948][INFO ][o.e.p.PluginsService     ] [JavaEdgedeMac-Studio.local] loaded module [x-pack-apm-data]
[2025-11-28T13:25:31,948][INFO ][o.e.p.PluginsService     ] [JavaEdgedeMac-Studio.local] loaded module [unsigned-long]
[2025-11-28T13:25:31,949][INFO ][o.e.p.PluginsService     ] [JavaEdgedeMac-Studio.local] loaded module [x-pack-sql]
[2025-11-28T13:25:31,949][INFO ][o.e.p.PluginsService     ] [JavaEdgedeMac-Studio.local] loaded module [runtime-fields-common]
[2025-11-28T13:25:31,949][INFO ][o.e.p.PluginsService     ] [JavaEdgedeMac-Studio.local] loaded module [x-pack-async]
[2025-11-28T13:25:31,949][INFO ][o.e.p.PluginsService     ] [JavaEdgedeMac-Studio.local] loaded module [vector-tile]
[2025-11-28T13:25:31,949][INFO ][o.e.p.PluginsService     ] [JavaEdgedeMac-Studio.local] loaded module [x-pack-kql]
[2025-11-28T13:25:31,949][INFO ][o.e.p.PluginsService     ] [JavaEdgedeMac-Studio.local] loaded module [lang-expression]
[2025-11-28T13:25:31,949][INFO ][o.e.p.PluginsService     ] [JavaEdgedeMac-Studio.local] loaded module [ingest-otel]
[2025-11-28T13:25:31,949][INFO ][o.e.p.PluginsService     ] [JavaEdgedeMac-Studio.local] loaded module [x-pack-eql]
[2025-11-28T13:25:32,060][WARN ][stderr                   ] [JavaEdgedeMac-Studio.local] SLF4J: No SLF4J providers were found.
[2025-11-28T13:25:32,060][WARN ][stderr                   ] [JavaEdgedeMac-Studio.local] SLF4J: Defaulting to no-operation (NOP) logger implementation
[2025-11-28T13:25:32,060][WARN ][stderr                   ] [JavaEdgedeMac-Studio.local] SLF4J: See https://www.slf4j.org/codes.html#noProviders for further details.
[2025-11-28T13:25:32,441][INFO ][o.e.e.NodeEnvironment    ] [JavaEdgedeMac-Studio.local] using [1] data paths, mounts [[/System/Volumes/Data (/dev/disk3s5)]], net usable_space [85.5gb], net total_space [460.4gb], types [apfs]
[2025-11-28T13:25:32,441][INFO ][o.e.e.NodeEnvironment    ] [JavaEdgedeMac-Studio.local] heap size [31gb], compressed ordinary object pointers [true]
[2025-11-28T13:25:32,458][INFO ][o.e.n.Node               ] [JavaEdgedeMac-Studio.local] node name [JavaEdgedeMac-Studio.local], node ID [BwZsYtX-QmylTPMKfyS9iw], cluster name [elasticsearch], roles [data_cold, data, remote_cluster_client, master, data_warm, data_content, transform, data_hot, ml, data_frozen, ingest]
[2025-11-28T13:25:33,256][INFO ][o.e.f.FeatureService     ] [JavaEdgedeMac-Studio.local] Registered local node features [ES_V_8, ES_V_9, cluster.reroute.ignores_metric_param, cluster.stats.source_modes, data_stream.failure_store, inference.endpoint.cache, ingest.field_access_pattern, linear_retriever_supported, lucene_10_1_upgrade, lucene_10_upgrade, resolve_index_returns_mode, security.queryable_built_in_roles, security_stats_endpoint, simulate.ignored.fields, snapshots.get.state_parameter]
[2025-11-28T13:25:33,330][INFO ][o.e.i.r.RecoverySettings ] [JavaEdgedeMac-Studio.local] using rate limit [40mb] with [default=40mb, read=0b, write=0b, max=0b]
[2025-11-28T13:25:33,392][INFO ][o.e.c.m.DataStreamGlobalRetentionSettings] [JavaEdgedeMac-Studio.local] Updated global default retention to [null]
[2025-11-28T13:25:33,392][INFO ][o.e.c.m.DataStreamGlobalRetentionSettings] [JavaEdgedeMac-Studio.local] Updated global max retention to [null]
[2025-11-28T13:25:33,393][INFO ][o.e.c.m.DataStreamGlobalRetentionSettings] [JavaEdgedeMac-Studio.local] Updated failures default retention to [30d]
[2025-11-28T13:25:33,393][INFO ][o.e.c.m.DataStreamFailureStoreSettings] [JavaEdgedeMac-Studio.local] Updated data stream name patterns for enabling failure store to [[]]
[2025-11-28T13:25:33,526][INFO ][o.e.x.m.p.l.CppLogMessageHandler] [JavaEdgedeMac-Studio.local] [controller/80014] [Main.cc@123] controller (64 bit): Version 9.2.1 (Build 5b1f3417ee0e02) Copyright (c) 2025 Elasticsearch BV
[2025-11-28T13:25:33,672][INFO ][o.e.x.o.OTelPlugin       ] [JavaEdgedeMac-Studio.local] OTel ingest plugin is enabled
[2025-11-28T13:25:33,683][INFO ][o.e.x.c.t.YamlTemplateRegistry] [JavaEdgedeMac-Studio.local] OpenTelemetry index template registry is enabled
[2025-11-28T13:25:33,685][INFO ][o.e.t.a.APM              ] [JavaEdgedeMac-Studio.local] Sending apm metrics is disabled
[2025-11-28T13:25:33,685][INFO ][o.e.t.a.APM              ] [JavaEdgedeMac-Studio.local] Sending apm tracing is disabled
[2025-11-28T13:25:33,708][INFO ][o.e.x.s.Security         ] [JavaEdgedeMac-Studio.local] Security is enabled
[2025-11-28T13:25:33,852][INFO ][o.e.x.s.a.s.FileRolesStore] [JavaEdgedeMac-Studio.local] parsed [0] roles from file [/Users/javaedge/soft/elasticsearch-9.2.1/config/roles.yml]

[2025-11-28T13:25:34,009][INFO ][o.e.x.w.Watcher          ] [JavaEdgedeMac-Studio.local] Watcher initialized components at 2025-11-28T05:25:34.009Z
[2025-11-28T13:25:34,047][INFO ][o.e.x.p.ProfilingPlugin  ] [JavaEdgedeMac-Studio.local] Profiling is enabled
[2025-11-28T13:25:34,052][INFO ][o.e.x.p.ProfilingPlugin  ] [JavaEdgedeMac-Studio.local] profiling index templates will not be installed or reinstalled
[2025-11-28T13:25:34,056][INFO ][o.e.x.a.APMPlugin        ] [JavaEdgedeMac-Studio.local] APM ingest plugin is enabled
[2025-11-28T13:25:34,066][INFO ][o.e.x.c.t.YamlTemplateRegistry] [JavaEdgedeMac-Studio.local] apm index template registry is enabled
[2025-11-28T13:25:34,200][WARN ][stderr                   ] [JavaEdgedeMac-Studio.local] SLF4J: No SLF4J providers were found.
[2025-11-28T13:25:34,200][WARN ][stderr                   ] [JavaEdgedeMac-Studio.local] SLF4J: Defaulting to no-operation (NOP) logger implementation
[2025-11-28T13:25:34,200][WARN ][stderr                   ] [JavaEdgedeMac-Studio.local] SLF4J: See https://www.slf4j.org/codes.html#noProviders for further details.
[2025-11-28T13:25:34,201][WARN ][stderr                   ] [JavaEdgedeMac-Studio.local] SLF4J: Class path contains SLF4J bindings targeting slf4j-api versions 1.7.x or earlier.
[2025-11-28T13:25:34,201][WARN ][stderr                   ] [JavaEdgedeMac-Studio.local] SLF4J: Ignoring binding found at [jar:file:///Users/javaedge/soft/elasticsearch-9.2.1/modules/x-pack-core/log4j-slf4j-impl-2.19.0.jar!/org/slf4j/impl/StaticLoggerBinder.class]
[2025-11-28T13:25:34,202][WARN ][stderr                   ] [JavaEdgedeMac-Studio.local] SLF4J: See https://www.slf4j.org/codes.html#ignoredBindings for an explanation.
[2025-11-28T13:25:34,213][INFO ][o.e.t.n.NettyAllocator   ] [JavaEdgedeMac-Studio.local] creating NettyAllocator with the following configs: [name=elasticsearch_configured, chunk_size=1mb, suggested_max_allocation_size=1mb, factors={es.unsafe.use_netty_default_chunk_and_page_size=false, g1gc_enabled=true, g1gc_region_size=16mb}]
[2025-11-28T13:25:34,235][INFO ][o.e.d.DiscoveryModule    ] [JavaEdgedeMac-Studio.local] using discovery type [multi-node] and seed hosts providers [settings]
[2025-11-28T13:25:34,624][INFO ][o.e.n.Node               ] [JavaEdgedeMac-Studio.local] initialized
[2025-11-28T13:25:34,624][INFO ][o.e.n.Node               ] [JavaEdgedeMac-Studio.local] starting ...
[2025-11-28T13:25:39,437][INFO ][o.e.x.s.c.f.PersistentCache] [JavaEdgedeMac-Studio.local] persistent cache index loaded
[2025-11-28T13:25:39,437][INFO ][o.e.x.d.l.DeprecationIndexingComponent] [JavaEdgedeMac-Studio.local] deprecation component started
[2025-11-28T13:25:39,476][INFO ][o.e.t.TransportService   ] [JavaEdgedeMac-Studio.local] publish_address {127.0.0.1:9300}, bound_addresses {[::1]:9300}, {127.0.0.1:9300}
[2025-11-28T13:25:39,534][INFO ][o.e.c.c.ClusterBootstrapService] [JavaEdgedeMac-Studio.local] this node has not joined a bootstrapped cluster yet; [cluster.initial_master_nodes] is set to [JavaEdgedeMac-Studio.local]
[2025-11-28T13:25:39,536][INFO ][o.e.c.c.Coordinator      ] [JavaEdgedeMac-Studio.local] setting initial configuration to VotingConfiguration{BwZsYtX-QmylTPMKfyS9iw}
[2025-11-28T13:25:39,633][INFO ][o.e.c.s.MasterService    ] [JavaEdgedeMac-Studio.local] elected-as-master ([1] nodes joined in term 1)[_FINISH_ELECTION_, {JavaEdgedeMac-Studio.local}{BwZsYtX-QmylTPMKfyS9iw}{FDIyLGGBQ3-J-kQNVZOhZQ}{JavaEdgedeMac-Studio.local}{127.0.0.1}{127.0.0.1:9300}{cdfhilmrstw}{9.2.1}{8000099-9039001} completing election], term: 1, version: 1, delta: master node changed {previous [], current [{JavaEdgedeMac-Studio.local}{BwZsYtX-QmylTPMKfyS9iw}{FDIyLGGBQ3-J-kQNVZOhZQ}{JavaEdgedeMac-Studio.local}{127.0.0.1}{127.0.0.1:9300}{cdfhilmrstw}{9.2.1}{8000099-9039001}]}
[2025-11-28T13:25:39,652][INFO ][o.e.c.c.CoordinationState] [JavaEdgedeMac-Studio.local] cluster UUID set to [fD6DLufLSyKa01KPTu8eCA]
[2025-11-28T13:25:39,668][INFO ][o.e.c.s.ClusterApplierService] [JavaEdgedeMac-Studio.local] master node changed {previous [], current [{JavaEdgedeMac-Studio.local}{BwZsYtX-QmylTPMKfyS9iw}{FDIyLGGBQ3-J-kQNVZOhZQ}{JavaEdgedeMac-Studio.local}{127.0.0.1}{127.0.0.1:9300}{cdfhilmrstw}{9.2.1}{8000099-9039001}]}, term: 1, version: 1, reason: Publication{term=1, version=1}
[2025-11-28T13:25:39,683][INFO ][o.e.c.c.NodeJoinExecutor ] [JavaEdgedeMac-Studio.local] node-join: [{JavaEdgedeMac-Studio.local}{BwZsYtX-QmylTPMKfyS9iw}{FDIyLGGBQ3-J-kQNVZOhZQ}{JavaEdgedeMac-Studio.local}{127.0.0.1}{127.0.0.1:9300}{cdfhilmrstw}{9.2.1}{8000099-9039001}] with reason [completing election]
[2025-11-28T13:25:39,685][INFO ][o.e.h.AbstractHttpServerTransport] [JavaEdgedeMac-Studio.local] publish_address {198.18.0.1:9200}, bound_addresses {[::]:9200}
[2025-11-28T13:25:39,687][INFO ][o.e.n.Node               ] [JavaEdgedeMac-Studio.local] started {JavaEdgedeMac-Studio.local}{BwZsYtX-QmylTPMKfyS9iw}{FDIyLGGBQ3-J-kQNVZOhZQ}{JavaEdgedeMac-Studio.local}{127.0.0.1}{127.0.0.1:9300}{cdfhilmrstw}{9.2.1}{8000099-9039001}{ml.machine_memory=137438953472, transform.config_version=10.0.0, xpack.installed=true, ml.config_version=12.0.0, ml.max_jvm_size=33285996544, ml.allocated_processors_double=16.0, ml.allocated_processors=16}
[2025-11-28T13:25:39,713][INFO ][o.e.x.m.MlIndexRollover  ] [JavaEdgedeMac-Studio.local] ML legacy indices rolled over
[2025-11-28T13:25:39,714][INFO ][o.e.x.m.MlAnomaliesIndexUpdate] [JavaEdgedeMac-Studio.local] legacy ml anomalies indices rolled over and aliases updated
[2025-11-28T13:25:39,719][INFO ][o.e.c.f.AbstractFileWatchingService] [JavaEdgedeMac-Studio.local] starting file watcher ...
[2025-11-28T13:25:39,722][INFO ][o.e.c.f.AbstractFileWatchingService] [JavaEdgedeMac-Studio.local] file settings service up and running [tid=124]
[2025-11-28T13:25:39,722][INFO ][o.e.r.s.FileSettingsService] [JavaEdgedeMac-Studio.local] setting file [/Users/javaedge/soft/elasticsearch-9.2.1/config/operator/settings.json] not found, initializing [file_settings] as empty
[2025-11-28T13:25:39,728][INFO ][o.e.x.s.s.SecurityIndexManager] [JavaEdgedeMac-Studio.local] security index does not exist, creating [.security-7] with alias [.security] in project [default]
[2025-11-28T13:25:39,728][INFO ][o.e.g.GatewayService     ] [JavaEdgedeMac-Studio.local] recovered [0] indices into cluster_state
[2025-11-28T13:25:39,795][INFO ][o.e.c.m.MetadataIndexTemplateService] [JavaEdgedeMac-Studio.local] adding index template [.monitoring-ent-search-mb] for index patterns [.monitoring-ent-search-8-*]
[2025-11-28T13:25:39,809][INFO ][o.e.c.m.MetadataIndexTemplateService] [JavaEdgedeMac-Studio.local] adding index template [.monitoring-beats-mb] for index patterns [.monitoring-beats-8-*]
[2025-11-28T13:25:39,814][INFO ][o.e.c.m.MetadataIndexTemplateService] [JavaEdgedeMac-Studio.local] adding index template [.monitoring-kibana-mb] for index patterns [.monitoring-kibana-8-*]
[2025-11-28T13:25:39,822][INFO ][o.e.c.m.MetadataIndexTemplateService] [JavaEdgedeMac-Studio.local] adding index template [.monitoring-logstash-mb] for index patterns [.monitoring-logstash-8-*]
[2025-11-28T13:25:39,836][INFO ][o.e.c.m.MetadataIndexTemplateService] [JavaEdgedeMac-Studio.local] adding index template [.monitoring-es-mb] for index patterns [.monitoring-es-8-*]
[2025-11-28T13:25:39,840][INFO ][o.e.c.m.MetadataIndexTemplateService] [JavaEdgedeMac-Studio.local] adding component template [elastic-connectors-mappings]
[2025-11-28T13:25:39,845][INFO ][o.e.c.m.MetadataIndexTemplateService] [JavaEdgedeMac-Studio.local] adding component template [behavioral_analytics-events-mappings]
[2025-11-28T13:25:39,847][INFO ][o.e.c.m.MetadataIndexTemplateService] [JavaEdgedeMac-Studio.local] adding component template [elastic-connectors-sync-jobs-mappings]
[2025-11-28T13:25:39,849][INFO ][o.e.c.m.MetadataIndexTemplateService] [JavaEdgedeMac-Studio.local] adding template [.monitoring-logstash] for index patterns [.monitoring-logstash-7-*]
[2025-11-28T13:25:39,851][INFO ][o.e.c.m.MetadataIndexTemplateService] [JavaEdgedeMac-Studio.local] adding template [.monitoring-kibana] for index patterns [.monitoring-kibana-7-*]
[2025-11-28T13:25:39,854][INFO ][o.e.c.m.MetadataIndexTemplateService] [JavaEdgedeMac-Studio.local] adding template [.monitoring-beats] for index patterns [.monitoring-beats-7-*]
[2025-11-28T13:25:39,855][INFO ][o.e.c.m.MetadataIndexTemplateService] [JavaEdgedeMac-Studio.local] adding template [.monitoring-alerts-7] for index patterns [.monitoring-alerts-7]
[2025-11-28T13:25:39,858][INFO ][o.e.c.m.MetadataIndexTemplateService] [JavaEdgedeMac-Studio.local] adding template [.monitoring-es] for index patterns [.monitoring-es-7-*]
[2025-11-28T13:25:39,858][INFO ][o.e.c.m.MetadataIndexTemplateService] [JavaEdgedeMac-Studio.local] adding component template [elastic-connectors-settings]
[2025-11-28T13:25:39,859][INFO ][o.e.c.m.MetadataIndexTemplateService] [JavaEdgedeMac-Studio.local] adding component template [elastic-connectors-sync-jobs-settings]
[2025-11-28T13:25:39,865][INFO ][o.e.c.m.MetadataIndexTemplateService] [JavaEdgedeMac-Studio.local] adding index template [.ml-anomalies-] for index patterns [.ml-anomalies-*, .reindexed-v7-ml-anomalies-*]
[2025-11-28T13:25:39,867][INFO ][o.e.c.m.MetadataIndexTemplateService] [JavaEdgedeMac-Studio.local] adding index template [.ml-notifications-000002] for index patterns [.ml-notifications-*]
[2025-11-28T13:25:39,869][INFO ][o.e.c.m.MetadataIndexTemplateService] [JavaEdgedeMac-Studio.local] adding index template [.ml-stats] for index patterns [.ml-stats-*]
[2025-11-28T13:25:39,870][INFO ][o.e.c.m.MetadataIndexTemplateService] [JavaEdgedeMac-Studio.local] adding index template [.ml-state] for index patterns [.ml-state*]
[2025-11-28T13:25:39,871][INFO ][o.e.c.m.MetadataIndexTemplateService] [JavaEdgedeMac-Studio.local] adding index template [search-acl-filter] for index patterns [.search-acl-filter-*]
[2025-11-28T13:25:39,871][INFO ][o.e.c.m.MetadataIndexTemplateService] [JavaEdgedeMac-Studio.local] adding component template [otel@settings]
[2025-11-28T13:25:39,873][INFO ][o.e.c.m.MetadataIndexTemplateService] [JavaEdgedeMac-Studio.local] adding component template [semconv-resource-to-ecs@mappings]
[2025-11-28T13:25:39,874][INFO ][o.e.c.m.MetadataIndexTemplateService] [JavaEdgedeMac-Studio.local] adding component template [metrics-otel@mappings]
[2025-11-28T13:25:39,876][INFO ][o.e.c.m.MetadataIndexTemplateService] [JavaEdgedeMac-Studio.local] adding component template [otel@mappings]
[2025-11-28T13:25:39,877][INFO ][o.e.c.m.MetadataIndexTemplateService] [JavaEdgedeMac-Studio.local] adding component template [ecs-tsdb@mappings]
[2025-11-28T13:25:39,882][INFO ][o.e.c.m.MetadataIndexTemplateService] [JavaEdgedeMac-Studio.local] adding component template [traces-otel@mappings]
[2025-11-28T13:25:39,885][INFO ][o.e.c.m.MetadataIndexTemplateService] [JavaEdgedeMac-Studio.local] adding component template [logs-otel@mappings]
[2025-11-28T13:25:39,886][INFO ][o.e.c.m.MetadataIndexTemplateService] [JavaEdgedeMac-Studio.local] adding component template [data-streams-mappings]
[2025-11-28T13:25:39,887][INFO ][o.e.c.m.MetadataIndexTemplateService] [JavaEdgedeMac-Studio.local] adding component template [metrics-tsdb-settings]
[2025-11-28T13:25:39,887][INFO ][o.e.c.m.MetadataIndexTemplateService] [JavaEdgedeMac-Studio.local] adding component template [logs-mappings]
[2025-11-28T13:25:39,888][INFO ][o.e.c.m.MetadataIndexTemplateService] [JavaEdgedeMac-Studio.local] adding component template [synthetics-mappings]
[2025-11-28T13:25:39,889][INFO ][o.e.c.m.MetadataIndexTemplateService] [JavaEdgedeMac-Studio.local] adding component template [synthetics-settings]
[2025-11-28T13:25:39,889][INFO ][o.e.c.m.MetadataIndexTemplateService] [JavaEdgedeMac-Studio.local] adding component template [metrics-settings]
[2025-11-28T13:25:39,890][INFO ][o.e.c.m.MetadataIndexTemplateService] [JavaEdgedeMac-Studio.local] adding component template [agentless@mappings]
[2025-11-28T13:25:39,890][INFO ][o.e.c.m.MetadataIndexTemplateService] [JavaEdgedeMac-Studio.local] adding component template [metrics@settings]
[2025-11-28T13:25:39,891][INFO ][o.e.c.m.MetadataIndexTemplateService] [JavaEdgedeMac-Studio.local] adding component template [metrics-mappings]
[2025-11-28T13:25:39,892][INFO ][o.e.c.m.MetadataIndexTemplateService] [JavaEdgedeMac-Studio.local] adding component template [ecs@dynamic_templates]
[2025-11-28T13:25:39,893][INFO ][o.e.c.m.MetadataIndexTemplateService] [JavaEdgedeMac-Studio.local] adding component template [data-streams@mappings]
[2025-11-28T13:25:39,894][INFO ][o.e.c.m.MetadataIndexTemplateService] [JavaEdgedeMac-Studio.local] adding component template [logs@mappings]
[2025-11-28T13:25:39,895][INFO ][o.e.c.m.MetadataIndexTemplateService] [JavaEdgedeMac-Studio.local] adding component template [traces@mappings]
[2025-11-28T13:25:39,895][INFO ][o.e.c.m.MetadataIndexTemplateService] [JavaEdgedeMac-Studio.local] adding component template [traces@settings]
[2025-11-28T13:25:39,896][INFO ][o.e.c.m.MetadataIndexTemplateService] [JavaEdgedeMac-Studio.local] adding component template [synthetics@settings]
[2025-11-28T13:25:39,897][INFO ][o.e.c.m.MetadataIndexTemplateService] [JavaEdgedeMac-Studio.local] adding component template [agentless@settings]
[2025-11-28T13:25:39,897][INFO ][o.e.c.m.MetadataIndexTemplateService] [JavaEdgedeMac-Studio.local] adding component template [metrics@mappings]
[2025-11-28T13:25:39,898][INFO ][o.e.c.m.MetadataIndexTemplateService] [JavaEdgedeMac-Studio.local] adding component template [ecs@mappings]
[2025-11-28T13:25:39,899][INFO ][o.e.c.m.MetadataIndexTemplateService] [JavaEdgedeMac-Studio.local] adding component template [kibana-reporting@settings]
[2025-11-28T13:25:39,899][INFO ][o.e.c.m.MetadataIndexTemplateService] [JavaEdgedeMac-Studio.local] adding component template [metrics@tsdb-settings]
[2025-11-28T13:25:39,900][INFO ][o.e.c.m.MetadataIndexTemplateService] [JavaEdgedeMac-Studio.local] adding component template [synthetics@mappings]
[2025-11-28T13:25:39,902][INFO ][o.e.c.m.MetadataIndexTemplateService] [JavaEdgedeMac-Studio.local] adding index template [.slm-history-7] for index patterns [.slm-history-7*]
[2025-11-28T13:25:39,905][INFO ][o.e.c.m.MetadataIndexTemplateService] [JavaEdgedeMac-Studio.local] adding index template [.watch-history-17] for index patterns [.watcher-history-17*]
[2025-11-28T13:25:39,906][INFO ][o.e.c.m.MetadataIndexTemplateService] [JavaEdgedeMac-Studio.local] adding index template [ilm-history-7] for index patterns [ilm-history-7*]
[2025-11-28T13:25:39,907][INFO ][o.e.c.m.MetadataIndexTemplateService] [JavaEdgedeMac-Studio.local] adding component template [.deprecation-indexing-mappings]
[2025-11-28T13:25:39,908][INFO ][o.e.c.m.MetadataIndexTemplateService] [JavaEdgedeMac-Studio.local] adding component template [.deprecation-indexing-settings]
[2025-11-28T13:25:39,909][INFO ][o.e.c.m.MetadataIndexTemplateService] [JavaEdgedeMac-Studio.local] adding index template [.fleet-fileds-tohost-data] for index patterns [.fleet-fileds-tohost-data-*]
[2025-11-28T13:25:39,910][INFO ][o.e.c.m.MetadataIndexTemplateService] [JavaEdgedeMac-Studio.local] adding index template [.fleet-fileds-tohost-meta] for index patterns [.fleet-fileds-tohost-meta-*]
[2025-11-28T13:25:39,911][INFO ][o.e.c.m.MetadataIndexTemplateService] [JavaEdgedeMac-Studio.local] adding index template [.fleet-fileds-fromhost-data] for index patterns [.fleet-fileds-fromhost-data-*]
[2025-11-28T13:25:39,912][INFO ][o.e.c.m.MetadataIndexTemplateService] [JavaEdgedeMac-Studio.local] adding index template [.fleet-fileds-fromhost-meta] for index patterns [.fleet-fileds-fromhost-meta-*]
[2025-11-28T13:25:39,915][INFO ][o.e.c.m.MetadataIndexTemplateService] [JavaEdgedeMac-Studio.local] adding component template [metrics-apm.service_destination@mappings]
[2025-11-28T13:25:39,916][INFO ][o.e.c.m.MetadataIndexTemplateService] [JavaEdgedeMac-Studio.local] adding component template [traces-apm.sampled-fallback@ilm]
[2025-11-28T13:25:39,917][INFO ][o.e.c.m.MetadataIndexTemplateService] [JavaEdgedeMac-Studio.local] adding component template [apm-10d@lifecycle]
[2025-11-28T13:25:39,917][INFO ][o.e.c.m.MetadataIndexTemplateService] [JavaEdgedeMac-Studio.local] adding component template [metrics-apm.service_destination.10m-fallback@ilm]
[2025-11-28T13:25:39,917][INFO ][o.e.c.m.MetadataIndexTemplateService] [JavaEdgedeMac-Studio.local] adding component template [metrics-apm.transaction.1m-fallback@ilm]
[2025-11-28T13:25:39,918][INFO ][o.e.c.m.MetadataIndexTemplateService] [JavaEdgedeMac-Studio.local] adding component template [metrics-apm.service_destination.60m-fallback@ilm]
[2025-11-28T13:25:39,919][INFO ][o.e.c.m.MetadataIndexTemplateService] [JavaEdgedeMac-Studio.local] adding component template [metrics-apm.transaction@mappings]
[2025-11-28T13:25:39,919][INFO ][o.e.c.m.MetadataIndexTemplateService] [JavaEdgedeMac-Studio.local] adding component template [metrics-apm.service_summary.1m-fallback@ilm]
[2025-11-28T13:25:39,919][INFO ][o.e.c.m.MetadataIndexTemplateService] [JavaEdgedeMac-Studio.local] adding component template [apm-180d@lifecycle]
[2025-11-28T13:25:39,920][INFO ][o.e.c.m.MetadataIndexTemplateService] [JavaEdgedeMac-Studio.local] adding component template [apm@settings]
[2025-11-28T13:25:39,921][INFO ][o.e.c.m.MetadataIndexTemplateService] [JavaEdgedeMac-Studio.local] adding component template [traces-apm.rum@mappings]
[2025-11-28T13:25:39,921][INFO ][o.e.c.m.MetadataIndexTemplateService] [JavaEdgedeMac-Studio.local] adding component template [traces-apm.rum-fallback@ilm]
[2025-11-28T13:25:39,922][INFO ][o.e.c.m.MetadataIndexTemplateService] [JavaEdgedeMac-Studio.local] adding component template [apm@mappings]
[2025-11-28T13:25:39,922][INFO ][o.e.c.m.MetadataIndexTemplateService] [JavaEdgedeMac-Studio.local] adding component template [metrics-apm.service_summary@mappings]
[2025-11-28T13:25:39,923][INFO ][o.e.c.m.MetadataIndexTemplateService] [JavaEdgedeMac-Studio.local] adding component template [metrics-apm.service_transaction.10m-fallback@ilm]
[2025-11-28T13:25:39,923][INFO ][o.e.c.m.MetadataIndexTemplateService] [JavaEdgedeMac-Studio.local] adding component template [metrics-apm.transaction.10m-fallback@ilm]
[2025-11-28T13:25:39,923][INFO ][o.e.c.m.MetadataIndexTemplateService] [JavaEdgedeMac-Studio.local] adding component template [logs-apm.app-fallback@ilm]
[2025-11-28T13:25:39,924][INFO ][o.e.c.m.MetadataIndexTemplateService] [JavaEdgedeMac-Studio.local] adding component template [metrics-apm@mappings]
[2025-11-28T13:25:39,924][INFO ][o.e.c.m.MetadataIndexTemplateService] [JavaEdgedeMac-Studio.local] adding component template [apm-390d@lifecycle]
[2025-11-28T13:25:39,925][INFO ][o.e.c.m.MetadataIndexTemplateService] [JavaEdgedeMac-Studio.local] adding component template [metrics-apm.service_destination.1m-fallback@ilm]
[2025-11-28T13:25:39,925][INFO ][o.e.c.m.MetadataIndexTemplateService] [JavaEdgedeMac-Studio.local] adding component template [traces-apm@mappings]
[2025-11-28T13:25:39,926][INFO ][o.e.c.m.MetadataIndexTemplateService] [JavaEdgedeMac-Studio.local] adding component template [metrics-apm.service_transaction.60m-fallback@ilm]
[2025-11-28T13:25:39,926][INFO ][o.e.c.m.MetadataIndexTemplateService] [JavaEdgedeMac-Studio.local] adding component template [metrics-apm.app-fallback@ilm]
[2025-11-28T13:25:39,927][INFO ][o.e.c.m.MetadataIndexTemplateService] [JavaEdgedeMac-Studio.local] adding component template [metrics-apm.service_transaction.1m-fallback@ilm]
[2025-11-28T13:25:39,927][INFO ][o.e.c.m.MetadataIndexTemplateService] [JavaEdgedeMac-Studio.local] adding component template [apm-90d@lifecycle]
[2025-11-28T13:25:39,928][INFO ][o.e.c.m.MetadataIndexTemplateService] [JavaEdgedeMac-Studio.local] adding component template [metrics-apm.internal-fallback@ilm]
[2025-11-28T13:25:39,928][INFO ][o.e.c.m.MetadataIndexTemplateService] [JavaEdgedeMac-Studio.local] adding component template [metrics-apm.service_transaction@mappings]
[2025-11-28T13:25:39,941][INFO ][o.e.c.m.MetadataIndexTemplateService] [JavaEdgedeMac-Studio.local] adding component template [logs-apm.error@mappings]
[2025-11-28T13:25:39,942][INFO ][o.e.c.m.MetadataIndexTemplateService] [JavaEdgedeMac-Studio.local] adding component template [traces-apm-fallback@ilm]
[2025-11-28T13:25:39,942][INFO ][o.e.c.m.MetadataIndexTemplateService] [JavaEdgedeMac-Studio.local] adding component template [metrics-apm.service_summary.60m-fallback@ilm]
[2025-11-28T13:25:39,943][INFO ][o.e.c.m.MetadataIndexTemplateService] [JavaEdgedeMac-Studio.local] adding component template [metrics-apm.service_summary.10m-fallback@ilm]
[2025-11-28T13:25:39,943][INFO ][o.e.c.m.MetadataIndexTemplateService] [JavaEdgedeMac-Studio.local] adding component template [metrics-apm@settings]
[2025-11-28T13:25:39,944][INFO ][o.e.c.m.MetadataIndexTemplateService] [JavaEdgedeMac-Studio.local] adding component template [logs-apm@settings]
[2025-11-28T13:25:39,944][INFO ][o.e.c.m.MetadataIndexTemplateService] [JavaEdgedeMac-Studio.local] adding component template [logs-apm.error-fallback@ilm]
[2025-11-28T13:25:39,944][INFO ][o.e.c.m.MetadataIndexTemplateService] [JavaEdgedeMac-Studio.local] adding component template [metrics-apm.transaction.60m-fallback@ilm]
[2025-11-28T13:25:40,020][INFO ][o.e.c.m.MetadataCreateIndexService] [JavaEdgedeMac-Studio.local] creating index [.security-7] in project [default], cause [api], templates [], shards [1]/[1]
[2025-11-28T13:25:40,024][INFO ][o.e.c.r.a.AllocationService] [JavaEdgedeMac-Studio.local] in project [default] updating number_of_replicas to [0] for indices [.security-7]
[2025-11-28T13:25:40,024][INFO ][o.e.x.w.LicensedWriteLoadForecaster] [JavaEdgedeMac-Studio.local] license state changed, now [valid]
[2025-11-28T13:25:40,047][INFO ][o.e.c.m.MetadataIndexTemplateService] [JavaEdgedeMac-Studio.local] adding index template [elastic-connectors-sync-jobs] for index patterns [.elastic-connectors-sync-jobs-v1]
[2025-11-28T13:25:40,048][INFO ][o.e.c.m.MetadataIndexTemplateService] [JavaEdgedeMac-Studio.local] adding index template [elastic-connectors] for index patterns [.elastic-connectors-v1]
[2025-11-28T13:25:40,055][INFO ][o.e.c.m.MetadataIndexTemplateService] [JavaEdgedeMac-Studio.local] adding index template [metrics-otel@template] for index patterns [metrics-*.otel-*]
[2025-11-28T13:25:40,060][INFO ][o.e.c.m.MetadataIndexTemplateService] [JavaEdgedeMac-Studio.local] adding index template [metrics-service_destination.10m.otel@template] for index patterns [metrics-service_destination.10m.otel-*]
[2025-11-28T13:25:40,065][INFO ][o.e.c.m.MetadataIndexTemplateService] [JavaEdgedeMac-Studio.local] adding index template [metrics-service_transaction.10m.otel@template] for index patterns [metrics-service_transaction.10m.otel-*]
[2025-11-28T13:25:40,069][INFO ][o.e.c.m.MetadataIndexTemplateService] [JavaEdgedeMac-Studio.local] adding index template [metrics-service_summary.60m.otel@template] for index patterns [metrics-service_summary.60m.otel-*]
[2025-11-28T13:25:40,073][INFO ][o.e.c.m.MetadataIndexTemplateService] [JavaEdgedeMac-Studio.local] adding index template [metrics-service_summary.1m.otel@template] for index patterns [metrics-service_summary.1m.otel-*]
[2025-11-28T13:25:40,076][INFO ][o.e.c.m.MetadataIndexTemplateService] [JavaEdgedeMac-Studio.local] adding index template [traces-otel@template] for index patterns [traces-*.otel-*]
[2025-11-28T13:25:40,080][INFO ][o.e.c.m.MetadataIndexTemplateService] [JavaEdgedeMac-Studio.local] adding index template [metrics-service_summary.10m.otel@template] for index patterns [metrics-service_summary.10m.otel-*]
[2025-11-28T13:25:40,080][INFO ][o.e.c.m.MetadataIndexTemplateService] [JavaEdgedeMac-Studio.local] adding index template [agentless] for index patterns [agentless-*-*]
[2025-11-28T13:25:40,082][INFO ][o.e.c.m.MetadataIndexTemplateService] [JavaEdgedeMac-Studio.local] adding index template [synthetics] for index patterns [synthetics-*-*]
[2025-11-28T13:25:40,086][INFO ][o.e.c.m.MetadataIndexTemplateService] [JavaEdgedeMac-Studio.local] adding index template [metrics-service_destination.60m.otel@template] for index patterns [metrics-service_destination.60m.otel-*]
[2025-11-28T13:25:40,087][INFO ][o.e.c.m.MetadataIndexTemplateService] [JavaEdgedeMac-Studio.local] adding index template [metrics] for index patterns [metrics-*-*]
[2025-11-28T13:25:40,091][INFO ][o.e.c.m.MetadataIndexTemplateService] [JavaEdgedeMac-Studio.local] adding index template [metrics-transaction.10m.otel@template] for index patterns [metrics-transaction.10m.otel-*]
[2025-11-28T13:25:40,094][INFO ][o.e.c.m.MetadataIndexTemplateService] [JavaEdgedeMac-Studio.local] adding index template [metrics-service_transaction.1m.otel@template] for index patterns [metrics-service_transaction.1m.otel-*]
[2025-11-28T13:25:40,097][INFO ][o.e.c.m.MetadataIndexTemplateService] [JavaEdgedeMac-Studio.local] adding index template [metrics-service_transaction.60m.otel@template] for index patterns [metrics-service_transaction.60m.otel-*]
[2025-11-28T13:25:40,100][INFO ][o.e.c.m.MetadataIndexTemplateService] [JavaEdgedeMac-Studio.local] adding index template [metrics-service_destination.1m.otel@template] for index patterns [metrics-service_destination.1m.otel-*]
[2025-11-28T13:25:40,103][INFO ][o.e.c.m.MetadataIndexTemplateService] [JavaEdgedeMac-Studio.local] adding index template [metrics-transaction.1m.otel@template] for index patterns [metrics-transaction.1m.otel-*]
[2025-11-28T13:25:40,106][INFO ][o.e.c.m.MetadataIndexTemplateService] [JavaEdgedeMac-Studio.local] adding index template [metrics-transaction.60m.otel@template] for index patterns [metrics-transaction.60m.otel-*]
[2025-11-28T13:25:40,107][INFO ][o.e.c.m.MetadataIndexTemplateService] [JavaEdgedeMac-Studio.local] adding index template [.deprecation-indexing-template-9] for index patterns [.logs-elasticsearch.deprecation-*]
[2025-11-28T13:25:40,108][INFO ][o.e.c.m.MetadataIndexTemplateService] [JavaEdgedeMac-Studio.local] adding index template [.deprecation-indexing-template] for index patterns [.logs-deprecation.*]
[2025-11-28T13:25:40,110][INFO ][o.e.c.m.MetadataIndexTemplateService] [JavaEdgedeMac-Studio.local] adding index template [metrics-apm.internal@template] for index patterns [metrics-apm.internal-*]
[2025-11-28T13:25:40,113][INFO ][o.e.c.m.MetadataIndexTemplateService] [JavaEdgedeMac-Studio.local] adding index template [metrics-apm.transaction.10m@template] for index patterns [metrics-apm.transaction.10m-*]
[2025-11-28T13:25:40,115][INFO ][o.e.c.m.MetadataIndexTemplateService] [JavaEdgedeMac-Studio.local] adding index template [metrics-apm.service_transaction.1m@template] for index patterns [metrics-apm.service_transaction.1m-*]
[2025-11-28T13:25:40,117][INFO ][o.e.c.m.MetadataIndexTemplateService] [JavaEdgedeMac-Studio.local] adding index template [.kibana-reporting] for index patterns [.kibana-reporting*]
[2025-11-28T13:25:40,119][INFO ][o.e.c.m.MetadataIndexTemplateService] [JavaEdgedeMac-Studio.local] adding index template [metrics-apm.service_destination.1m@template] for index patterns [metrics-apm.service_destination.1m-*]
[2025-11-28T13:25:40,120][INFO ][o.e.c.m.MetadataIndexTemplateService] [JavaEdgedeMac-Studio.local] adding index template [metrics-apm.transaction.60m@template] for index patterns [metrics-apm.transaction.60m-*]
[2025-11-28T13:25:40,122][INFO ][o.e.c.m.MetadataIndexTemplateService] [JavaEdgedeMac-Studio.local] adding index template [metrics-apm.service_transaction.60m@template] for index patterns [metrics-apm.service_transaction.60m-*]
[2025-11-28T13:25:40,123][INFO ][o.e.c.m.MetadataIndexTemplateService] [JavaEdgedeMac-Studio.local] adding index template [metrics-apm.service_destination.10m@template] for index patterns [metrics-apm.service_destination.10m-*]
[2025-11-28T13:25:40,125][INFO ][o.e.c.m.MetadataIndexTemplateService] [JavaEdgedeMac-Studio.local] adding index template [metrics-apm.service_summary.10m@template] for index patterns [metrics-apm.service_summary.10m-*]
[2025-11-28T13:25:40,127][INFO ][o.e.c.m.MetadataIndexTemplateService] [JavaEdgedeMac-Studio.local] adding index template [metrics-apm.service_destination.60m@template] for index patterns [metrics-apm.service_destination.60m-*]
[2025-11-28T13:25:40,129][INFO ][o.e.c.m.MetadataIndexTemplateService] [JavaEdgedeMac-Studio.local] adding index template [traces-apm.rum@template] for index patterns [traces-apm.rum-*]
[2025-11-28T13:25:40,134][INFO ][o.e.c.m.MetadataIndexTemplateService] [JavaEdgedeMac-Studio.local] adding index template [traces-apm.sampled@template] for index patterns [traces-apm.sampled-*]
[2025-11-28T13:25:40,136][INFO ][o.e.c.m.MetadataIndexTemplateService] [JavaEdgedeMac-Studio.local] adding index template [traces-apm@template] for index patterns [traces-apm-*]
[2025-11-28T13:25:40,138][INFO ][o.e.c.m.MetadataIndexTemplateService] [JavaEdgedeMac-Studio.local] adding index template [metrics-apm.transaction.1m@template] for index patterns [metrics-apm.transaction.1m-*]
[2025-11-28T13:25:40,140][INFO ][o.e.c.m.MetadataIndexTemplateService] [JavaEdgedeMac-Studio.local] adding index template [metrics-apm.service_summary.60m@template] for index patterns [metrics-apm.service_summary.60m-*]
[2025-11-28T13:25:40,142][INFO ][o.e.c.m.MetadataIndexTemplateService] [JavaEdgedeMac-Studio.local] adding index template [logs-apm.error@template] for index patterns [logs-apm.error-*]
[2025-11-28T13:25:40,144][INFO ][o.e.c.m.MetadataIndexTemplateService] [JavaEdgedeMac-Studio.local] adding index template [logs-apm.app@template] for index patterns [logs-apm.app.*-*]
[2025-11-28T13:25:40,146][INFO ][o.e.c.m.MetadataIndexTemplateService] [JavaEdgedeMac-Studio.local] adding index template [metrics-apm.app@template] for index patterns [metrics-apm.app.*-*]
[2025-11-28T13:25:40,147][INFO ][o.e.c.m.MetadataIndexTemplateService] [JavaEdgedeMac-Studio.local] adding index template [metrics-apm.service_summary.1m@template] for index patterns [metrics-apm.service_summary.1m-*]
[2025-11-28T13:25:40,149][INFO ][o.e.c.m.MetadataIndexTemplateService] [JavaEdgedeMac-Studio.local] adding index template [metrics-apm.service_transaction.10m@template] for index patterns [metrics-apm.service_transaction.10m-*]
[2025-11-28T13:25:40,222][INFO ][o.e.x.i.PutLifecycleMetadataService] [JavaEdgedeMac-Studio.local] adding index lifecycle policy [.monitoring-8-ilm-policy]
[2025-11-28T13:25:40,222][INFO ][o.e.x.i.PutLifecycleMetadataService] [JavaEdgedeMac-Studio.local] adding index lifecycle policy [ml-size-based-ilm-policy]
[2025-11-28T13:25:40,222][INFO ][o.e.x.i.PutLifecycleMetadataService] [JavaEdgedeMac-Studio.local] adding index lifecycle policy [180-days-default]
[2025-11-28T13:25:40,222][INFO ][o.e.x.i.PutLifecycleMetadataService] [JavaEdgedeMac-Studio.local] adding index lifecycle policy [365-days-default]
[2025-11-28T13:25:40,222][INFO ][o.e.x.i.PutLifecycleMetadataService] [JavaEdgedeMac-Studio.local] adding index lifecycle policy [30-days-default]
[2025-11-28T13:25:40,225][INFO ][o.e.x.i.PutLifecycleMetadataService] [JavaEdgedeMac-Studio.local] adding index lifecycle policy [synthetics]
[2025-11-28T13:25:40,225][INFO ][o.e.x.i.PutLifecycleMetadataService] [JavaEdgedeMac-Studio.local] adding index lifecycle policy [7-days-default]
[2025-11-28T13:25:40,225][INFO ][o.e.x.i.PutLifecycleMetadataService] [JavaEdgedeMac-Studio.local] adding index lifecycle policy [metrics]
[2025-11-28T13:25:40,225][INFO ][o.e.x.i.PutLifecycleMetadataService] [JavaEdgedeMac-Studio.local] adding index lifecycle policy [90-days-default]
[2025-11-28T13:25:40,226][INFO ][o.e.x.i.PutLifecycleMetadataService] [JavaEdgedeMac-Studio.local] adding index lifecycle policy [logs]
[2025-11-28T13:25:40,226][INFO ][o.e.x.i.PutLifecycleMetadataService] [JavaEdgedeMac-Studio.local] adding index lifecycle policy [synthetics@lifecycle]
[2025-11-28T13:25:40,226][INFO ][o.e.x.i.PutLifecycleMetadataService] [JavaEdgedeMac-Studio.local] adding index lifecycle policy [traces@lifecycle]
[2025-11-28T13:25:40,226][INFO ][o.e.x.i.PutLifecycleMetadataService] [JavaEdgedeMac-Studio.local] adding index lifecycle policy [30-days@lifecycle]
[2025-11-28T13:25:40,226][INFO ][o.e.x.i.PutLifecycleMetadataService] [JavaEdgedeMac-Studio.local] adding index lifecycle policy [90-days@lifecycle]
[2025-11-28T13:25:40,229][INFO ][o.e.x.i.PutLifecycleMetadataService] [JavaEdgedeMac-Studio.local] adding index lifecycle policy [metrics@lifecycle]
[2025-11-28T13:25:40,229][INFO ][o.e.x.i.PutLifecycleMetadataService] [JavaEdgedeMac-Studio.local] adding index lifecycle policy [logs@lifecycle]
[2025-11-28T13:25:40,229][INFO ][o.e.x.i.PutLifecycleMetadataService] [JavaEdgedeMac-Studio.local] adding index lifecycle policy [180-days@lifecycle]
[2025-11-28T13:25:40,229][INFO ][o.e.x.i.PutLifecycleMetadataService] [JavaEdgedeMac-Studio.local] adding index lifecycle policy [7-days@lifecycle]
[2025-11-28T13:25:40,230][INFO ][o.e.x.i.PutLifecycleMetadataService] [JavaEdgedeMac-Studio.local] adding index lifecycle policy [365-days@lifecycle]
[2025-11-28T13:25:40,230][INFO ][o.e.x.i.PutLifecycleMetadataService] [JavaEdgedeMac-Studio.local] adding index lifecycle policy [slm-history-ilm-policy]
[2025-11-28T13:25:40,230][INFO ][o.e.x.i.PutLifecycleMetadataService] [JavaEdgedeMac-Studio.local] adding index lifecycle policy [watch-history-ilm-policy-16]
[2025-11-28T13:25:40,230][INFO ][o.e.x.i.PutLifecycleMetadataService] [JavaEdgedeMac-Studio.local] adding index lifecycle policy [ilm-history-ilm-policy]
[2025-11-28T13:25:40,230][INFO ][o.e.x.i.PutLifecycleMetadataService] [JavaEdgedeMac-Studio.local] adding index lifecycle policy [.deprecation-indexing-ilm-policy]
[2025-11-28T13:25:40,230][INFO ][o.e.x.i.PutLifecycleMetadataService] [JavaEdgedeMac-Studio.local] adding index lifecycle policy [.fleet-actions-results-ilm-policy]
[2025-11-28T13:25:40,232][INFO ][o.e.x.i.PutLifecycleMetadataService] [JavaEdgedeMac-Studio.local] adding index lifecycle policy [.fleet-file-tohost-meta-ilm-policy]
[2025-11-28T13:25:40,232][INFO ][o.e.x.i.PutLifecycleMetadataService] [JavaEdgedeMac-Studio.local] adding index lifecycle policy [.fleet-file-tohost-data-ilm-policy]
[2025-11-28T13:25:40,232][INFO ][o.e.x.i.PutLifecycleMetadataService] [JavaEdgedeMac-Studio.local] adding index lifecycle policy [.fleet-file-fromhost-data-ilm-policy]
[2025-11-28T13:25:40,233][INFO ][o.e.x.i.PutLifecycleMetadataService] [JavaEdgedeMac-Studio.local] adding index lifecycle policy [.fleet-file-fromhost-meta-ilm-policy]
[2025-11-28T13:25:40,233][INFO ][o.e.x.i.PutLifecycleMetadataService] [JavaEdgedeMac-Studio.local] adding index lifecycle policy [logs-apm.app_logs-default_policy]
[2025-11-28T13:25:40,233][INFO ][o.e.x.i.PutLifecycleMetadataService] [JavaEdgedeMac-Studio.local] adding index lifecycle policy [metrics-apm.service_destination_1m_metrics-default_policy]
[2025-11-28T13:25:40,233][INFO ][o.e.x.i.PutLifecycleMetadataService] [JavaEdgedeMac-Studio.local] adding index lifecycle policy [metrics-apm.service_destination_60m_metrics-default_policy]
[2025-11-28T13:25:40,233][INFO ][o.e.x.i.PutLifecycleMetadataService] [JavaEdgedeMac-Studio.local] adding index lifecycle policy [metrics-apm.service_destination_10m_metrics-default_policy]
[2025-11-28T13:25:40,233][INFO ][o.e.x.i.PutLifecycleMetadataService] [JavaEdgedeMac-Studio.local] adding index lifecycle policy [metrics-apm.service_summary_10m_metrics-default_policy]
[2025-11-28T13:25:40,233][INFO ][o.e.x.i.PutLifecycleMetadataService] [JavaEdgedeMac-Studio.local] adding index lifecycle policy [metrics-apm.service_summary_1m_metrics-default_policy]
[2025-11-28T13:25:40,233][INFO ][o.e.x.i.PutLifecycleMetadataService] [JavaEdgedeMac-Studio.local] adding index lifecycle policy [metrics-apm.service_transaction_10m_metrics-default_policy]
[2025-11-28T13:25:40,233][INFO ][o.e.x.i.PutLifecycleMetadataService] [JavaEdgedeMac-Studio.local] adding index lifecycle policy [metrics-apm.internal_metrics-default_policy]
[2025-11-28T13:25:40,233][INFO ][o.e.x.i.PutLifecycleMetadataService] [JavaEdgedeMac-Studio.local] adding index lifecycle policy [metrics-apm.service_summary_60m_metrics-default_policy]
[2025-11-28T13:25:40,233][INFO ][o.e.x.i.PutLifecycleMetadataService] [JavaEdgedeMac-Studio.local] adding index lifecycle policy [metrics-apm.app_metrics-default_policy]
[2025-11-28T13:25:40,233][INFO ][o.e.x.i.PutLifecycleMetadataService] [JavaEdgedeMac-Studio.local] adding index lifecycle policy [metrics-apm.service_transaction_60m_metrics-default_policy]
[2025-11-28T13:25:40,233][INFO ][o.e.x.i.PutLifecycleMetadataService] [JavaEdgedeMac-Studio.local] adding index lifecycle policy [metrics-apm.service_transaction_1m_metrics-default_policy]
[2025-11-28T13:25:40,233][INFO ][o.e.x.i.PutLifecycleMetadataService] [JavaEdgedeMac-Studio.local] adding index lifecycle policy [metrics-apm.transaction_10m_metrics-default_policy]
[2025-11-28T13:25:40,233][INFO ][o.e.x.i.PutLifecycleMetadataService] [JavaEdgedeMac-Studio.local] adding index lifecycle policy [traces-apm.sampled_traces-default_policy]
[2025-11-28T13:25:40,234][INFO ][o.e.x.i.PutLifecycleMetadataService] [JavaEdgedeMac-Studio.local] adding index lifecycle policy [traces-apm.traces-default_policy]
[2025-11-28T13:25:40,234][INFO ][o.e.x.i.PutLifecycleMetadataService] [JavaEdgedeMac-Studio.local] adding index lifecycle policy [traces-apm.rum_traces-default_policy]
[2025-11-28T13:25:40,234][INFO ][o.e.x.i.PutLifecycleMetadataService] [JavaEdgedeMac-Studio.local] adding index lifecycle policy [metrics-apm.transaction_1m_metrics-default_policy]
[2025-11-28T13:25:40,234][INFO ][o.e.x.i.PutLifecycleMetadataService] [JavaEdgedeMac-Studio.local] adding index lifecycle policy [logs-apm.error_logs-default_policy]
[2025-11-28T13:25:40,234][INFO ][o.e.x.i.PutLifecycleMetadataService] [JavaEdgedeMac-Studio.local] adding index lifecycle policy [metrics-apm.transaction_60m_metrics-default_policy]
[2025-11-28T13:25:40,335][INFO ][o.e.x.c.t.IndexTemplateRegistry] [JavaEdgedeMac-Studio.local] adding ingest pipeline behavioral_analytics-events-final_pipeline
[2025-11-28T13:25:40,335][INFO ][o.e.x.c.t.IndexTemplateRegistry] [JavaEdgedeMac-Studio.local] adding ingest pipeline reindex-data-stream-pipeline
[2025-11-28T13:25:40,335][INFO ][o.e.x.c.t.IndexTemplateRegistry] [JavaEdgedeMac-Studio.local] adding ingest pipeline logs@default-pipeline
[2025-11-28T13:25:40,335][INFO ][o.e.x.c.t.IndexTemplateRegistry] [JavaEdgedeMac-Studio.local] adding ingest pipeline logs-default-pipeline
[2025-11-28T13:25:40,335][INFO ][o.e.x.c.t.IndexTemplateRegistry] [JavaEdgedeMac-Studio.local] adding ingest pipeline metrics-apm.service_summary@default-pipeline
[2025-11-28T13:25:40,335][INFO ][o.e.x.c.t.IndexTemplateRegistry] [JavaEdgedeMac-Studio.local] adding ingest pipeline traces-apm@default-pipeline
[2025-11-28T13:25:40,335][INFO ][o.e.x.c.t.IndexTemplateRegistry] [JavaEdgedeMac-Studio.local] adding ingest pipeline traces-apm.rum@default-pipeline
[2025-11-28T13:25:40,335][INFO ][o.e.x.c.t.IndexTemplateRegistry] [JavaEdgedeMac-Studio.local] adding ingest pipeline metrics-apm.transaction@default-pipeline
[2025-11-28T13:25:40,335][INFO ][o.e.x.c.t.IndexTemplateRegistry] [JavaEdgedeMac-Studio.local] adding ingest pipeline logs-apm.app@default-pipeline
[2025-11-28T13:25:40,335][INFO ][o.e.x.c.t.IndexTemplateRegistry] [JavaEdgedeMac-Studio.local] adding ingest pipeline metrics-apm.app@default-pipeline
[2025-11-28T13:25:40,335][INFO ][o.e.x.c.t.IndexTemplateRegistry] [JavaEdgedeMac-Studio.local] adding ingest pipeline logs-apm.error@default-pipeline
[2025-11-28T13:25:40,335][INFO ][o.e.x.c.t.IndexTemplateRegistry] [JavaEdgedeMac-Studio.local] adding ingest pipeline apm@pipeline
[2025-11-28T13:25:40,335][INFO ][o.e.x.c.t.IndexTemplateRegistry] [JavaEdgedeMac-Studio.local] adding ingest pipeline metrics-apm.service_destination@default-pipeline
[2025-11-28T13:25:40,335][INFO ][o.e.x.c.t.IndexTemplateRegistry] [JavaEdgedeMac-Studio.local] adding ingest pipeline metrics-apm.service_transaction@default-pipeline
[2025-11-28T13:25:40,335][INFO ][o.e.x.c.t.IndexTemplateRegistry] [JavaEdgedeMac-Studio.local] adding ingest pipeline search-default-ingestion
[2025-11-28T13:25:40,335][INFO ][o.e.x.c.t.IndexTemplateRegistry] [JavaEdgedeMac-Studio.local] adding ingest pipeline logs@json-pipeline
[2025-11-28T13:25:40,335][INFO ][o.e.x.c.t.IndexTemplateRegistry] [JavaEdgedeMac-Studio.local] adding ingest pipeline logs@json-message
[2025-11-28T13:25:40,336][INFO ][o.e.c.r.a.AllocationService] [JavaEdgedeMac-Studio.local] current.health="GREEN" message="Cluster health status changed from [YELLOW] to [GREEN] (reason: [shards started [[.security-7][0]]])." previous.health="YELLOW" reason="shards started [[.security-7][0]]"
[2025-11-28T13:25:40,358][INFO ][o.e.c.m.MetadataIndexTemplateService] [JavaEdgedeMac-Studio.local] adding component template [behavioral_analytics-events-settings]
[2025-11-28T13:25:40,358][INFO ][o.e.c.m.MetadataIndexTemplateService] [JavaEdgedeMac-Studio.local] adding component template [logs-settings]
[2025-11-28T13:25:40,362][INFO ][o.e.c.m.MetadataIndexTemplateService] [JavaEdgedeMac-Studio.local] adding component template [logs@settings]
[2025-11-28T13:25:40,391][INFO ][o.e.c.m.MetadataIndexTemplateService] [JavaEdgedeMac-Studio.local] adding index template [behavioral_analytics-events-default] for index patterns [behavioral_analytics-events-*]
[2025-11-28T13:25:40,394][INFO ][o.e.c.m.MetadataIndexTemplateService] [JavaEdgedeMac-Studio.local] adding index template [logs-otel@template] for index patterns [logs-*.otel-*]
[2025-11-28T13:25:40,395][INFO ][o.e.c.m.MetadataIndexTemplateService] [JavaEdgedeMac-Studio.local] adding index template [logs] for index patterns [logs-*-*]
[2025-11-28T13:25:40,514][INFO ][o.e.h.n.s.HealthNodeTaskExecutor] [JavaEdgedeMac-Studio.local] Node [{JavaEdgedeMac-Studio.local}{BwZsYtX-QmylTPMKfyS9iw}] is selected as the current health node.
[2025-11-28T13:25:40,543][INFO ][o.e.x.s.a.Realms         ] [JavaEdgedeMac-Studio.local] license mode is [basic], currently licensed security realms are [reserved/reserved,file/default_file,native/default_native]
[2025-11-28T13:25:40,544][INFO ][o.e.l.ClusterStateLicenseService] [JavaEdgedeMac-Studio.local] license [1e887a5e-8f40-4e78-90db-805013659cd1] mode [basic] - valid
[2025-11-28T13:25:40,603][INFO ][o.e.x.c.t.IndexTemplateRegistry] [JavaEdgedeMac-Studio.local] adding ingest pipeline logs-apm@pipeline
[2025-11-28T13:25:40,604][INFO ][o.e.x.c.t.IndexTemplateRegistry] [JavaEdgedeMac-Studio.local] adding ingest pipeline metrics-apm.internal@default-pipeline
[2025-11-28T13:25:40,604][INFO ][o.e.x.c.t.IndexTemplateRegistry] [JavaEdgedeMac-Studio.local] adding ingest pipeline metrics-apm@pipeline
[2025-11-28T13:25:40,604][INFO ][o.e.x.c.t.IndexTemplateRegistry] [JavaEdgedeMac-Studio.local] adding ingest pipeline traces-apm@pipeline
[2025-11-28T13:25:40,604][INFO ][o.e.x.w.LicensedWriteLoadForecaster] [JavaEdgedeMac-Studio.local] license state changed, now [not valid]
[2025-11-28T13:25:40,622][INFO ][o.e.x.s.s.QueryableBuiltInRolesSynchronizer] [JavaEdgedeMac-Studio.local] Successfully synced [29] built-in roles to .security index
[2025-11-28T13:25:40,645][INFO ][o.e.x.c.s.a.UpdateIndexMigrationVersionAction] [JavaEdgedeMac-Studio.local] Updated project=[default] index=[.security-7] to migration-version=[2]
[2025-11-28T13:25:40,648][INFO ][o.e.x.s.s.SecurityMigrationExecutor] [JavaEdgedeMac-Studio.local] Security migration not needed. Setting current version to: [2]
[2025-11-28T13:25:44,724][INFO ][o.e.c.m.MetadataCreateIndexService] [JavaEdgedeMac-Studio.local] creating index [.ds-.logs-elasticsearch.deprecation-default-2025.11.28-000001] in project [default], cause [initialize_data_stream], templates [provided in request], shards [1]/[1]
[2025-11-28T13:25:44,726][INFO ][o.e.c.m.MetadataCreateDataStreamService] [JavaEdgedeMac-Studio.local] adding data stream [.logs-elasticsearch.deprecation-default] with write index [.ds-.logs-elasticsearch.deprecation-default-2025.11.28-000001], backing indices [], and aliases []
[2025-11-28T13:25:44,727][INFO ][o.e.c.r.a.AllocationService] [JavaEdgedeMac-Studio.local] in project [default] updating number_of_replicas to [0] for indices [.ds-.logs-elasticsearch.deprecation-default-2025.11.28-000001]
[2025-11-28T13:25:44,791][INFO ][o.e.x.i.IndexLifecycleTransition] [JavaEdgedeMac-Studio.local] moving index [.ds-.logs-elasticsearch.deprecation-default-2025.11.28-000001] from [null] to [{"phase":"new","action":"complete","name":"complete"}] in policy [.deprecation-indexing-ilm-policy]
[2025-11-28T13:25:44,884][INFO ][o.e.x.i.IndexLifecycleTransition] [JavaEdgedeMac-Studio.local] moving index [.ds-.logs-elasticsearch.deprecation-default-2025.11.28-000001] from [{"phase":"new","action":"complete","name":"complete"}] to [{"phase":"hot","action":"unfollow","name":"branch-check-unfollow-prerequisites"}] in policy [.deprecation-indexing-ilm-policy]
[2025-11-28T13:25:44,919][INFO ][o.e.c.r.a.AllocationService] [JavaEdgedeMac-Studio.local] current.health="GREEN" message="Cluster health status changed from [YELLOW] to [GREEN] (reason: [shards started [[.ds-.logs-elasticsearch.deprecation-default-2025.11.28-000001][0]]])." previous.health="YELLOW" reason="shards started [[.ds-.logs-elasticsearch.deprecation-default-2025.11.28-000001][0]]"
[2025-11-28T13:25:44,945][INFO ][o.e.x.i.IndexLifecycleTransition] [JavaEdgedeMac-Studio.local] moving index [.ds-.logs-elasticsearch.deprecation-default-2025.11.28-000001] from [{"phase":"hot","action":"unfollow","name":"branch-check-unfollow-prerequisites"}] to [{"phase":"hot","action":"rollover","name":"check-rollover-ready"}] in policy [.deprecation-indexing-ilm-policy]
[2025-11-28T13:25:44,971][INFO ][o.e.c.m.MetadataMappingService] [JavaEdgedeMac-Studio.local] [.ds-.logs-elasticsearch.deprecation-default-2025.11.28-000001/yDgnH1CyQHaN0ZVSwYRuZA] update_mapping [_doc]
[2025-11-28T13:25:48,725][INFO ][o.e.x.s.InitialNodeSecurityAutoConfiguration] [JavaEdgedeMac-Studio.local] HTTPS has been configured with automatically generated certificates, and the CA's hex-encoded SHA-256 fingerprint is [1b9e9ab1ce7ed8506090623267db767f51efb8442489a4ddf6d30f92fd120d60]
[2025-11-28T13:25:48,731][INFO ][o.e.x.s.e.InternalEnrollmentTokenGenerator] [JavaEdgedeMac-Studio.local] Will not generate node enrollment token because node is only bound on localhost for transport and cannot connect to nodes from other hosts




━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
✅ Elasticsearch security features have been automatically configured!
✅ Authentication is enabled and cluster connections are encrypted.

ℹ️  Password for the elastic user (reset with `bin/elasticsearch-reset-password -u elastic`):
j+a8cdwmXnLj4JNPEyme

ℹ️  HTTP CA certificate SHA-256 fingerprint:
1b9e9ab1ce7ed8506090623267db767f51efb8442489a4ddf6d30f92fd120d60

ℹ️  Configure Kibana to use this cluster:
• Run Kibana and click the configuration link in the terminal when Kibana starts.
• Copy the following enrollment token and paste it into Kibana in your browser (valid for the next 30 minutes):
eyJ2ZXIiOiI4LjE0LjAiLCJhZHIiOlsiMTk4LjE4LjAuMTo5MjAwIl0sImZnciI6IjFiOWU5YWIxY2U3ZWQ4NTA2MDkwNjIzMjY3ZGI3NjdmNTFlZmI4NDQyNDg5YTRkZGY2ZDMwZjkyZmQxMjBkNjAiLCJrZXkiOiJId2pzeUpvQnc2LWt5Z0c5dW9SQjppUDVsUGNha2tObFhuTm1rdmxVeVJnIn0=

ℹ️  Configure other nodes to join this cluster:
• On this node:
⁃ Create an enrollment token with `bin/elasticsearch-create-enrollment-token -s node`.
⁃ Uncomment the transport.host setting at the end of config/elasticsearch.yml.
⁃ Restart Elasticsearch.
• On other nodes:
⁃ Start Elasticsearch with `bin/elasticsearch --enrollment-token <token>`, using the enrollment token that you generated.
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━




[2025-11-28T13:25:49,900][INFO ][o.e.c.m.MetadataCreateIndexService] [JavaEdgedeMac-Studio.local] creating index [.ds-ilm-history-7-2025.11.28-000001] in project [default], cause [initialize_data_stream], templates [provided in request], shards [1]/[1]
[2025-11-28T13:25:49,901][INFO ][o.e.c.m.MetadataCreateDataStreamService] [JavaEdgedeMac-Studio.local] adding data stream [ilm-history-7] with write index [.ds-ilm-history-7-2025.11.28-000001], backing indices [], and aliases []
[2025-11-28T13:25:49,901][INFO ][o.e.c.r.a.AllocationService] [JavaEdgedeMac-Studio.local] in project [default] updating number_of_replicas to [0] for indices [.ds-ilm-history-7-2025.11.28-000001]
[2025-11-28T13:25:50,020][INFO ][o.e.c.r.a.AllocationService] [JavaEdgedeMac-Studio.local] current.health="GREEN" message="Cluster health status changed from [YELLOW] to [GREEN] (reason: [shards started [[.ds-ilm-history-7-2025.11.28-000001][0]]])." previous.health="YELLOW" reason="shards started [[.ds-ilm-history-7-2025.11.28-000001][0]]"