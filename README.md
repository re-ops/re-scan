# Intro

A Clojure-fied Nmap wrapper.

[![Build Status](https://travis-ci.org/re-ops/re-gent.png)](https://travis-ci.org/re-ops/re-gent)


# Usage

In your Lein project:

[re-scan "0.1.0"]

```clojure
user=> (use 're-scan.core)
user=> (pprint (open-ports (nmap "/usr/bin/" "-T5" "foo")))
({"foo"
  ({:protocol "tcp",
    :portid "22",
    :state "open",
    :reason "syn-ack",
    :reason_ttl "64",
    :name "ssh",
    :method "table",
    :conf "3"}
    ...
```

# Prerequisite

* JDK 8
* lein
* password-less sudo Nmap access

# Copyright and license

Copyright [2017] [Ronen Narkis]

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

  [http://www.apache.org/licenses/LICENSE-2.0](http://www.apache.org/licenses/LICENSE-2.0)

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
