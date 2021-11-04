<?php

$redis = new Redis();
//Connecting to Redis
$redis->connect('localhost', 6379);
$redis->auth('CHANGEME');

if (! $redis->ping()) {
 echo "ERROR\n";
}

// Set value
$redis->set("hello", "Hello world");


$value = $redis->get("hello");

echo $value , " \n";

?>