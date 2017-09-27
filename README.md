# mackerel-egads
Anomaly detection for Mackerel

## Setup

### Install EGADS

```
% git clone https://github.com/yahoo/egads.git
% cd egads
% mvn install
```

## Execute

Execute `net.unit8.mackerel.Main` with following system properties:

|Name |Value|
|:----|:----|
|api.key|Mackerel API key|
|host.id|Mackerel host ID|
