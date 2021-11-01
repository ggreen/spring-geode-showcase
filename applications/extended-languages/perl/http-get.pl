#!/bin/perl
use LWP::Simple;
use JSON qw( decode_json );     # From CPAN
use Data::Dumper;               # Perl core module
use strict;                     # Good practice
use warnings;                   # Good practice



my $usageError = "Usage: <region> <key>\n";
my $region = shift or die $usageError;
my $regionKey = shift or die $usageError;
my $url = "http://localhost:8000/geode/v1/$region/$regionKey";

my $response = get $url;
print ("$response \n");

#my $response = get "http://localhost:8000/geode/v1/test/123";
#print ("$response \n");
#
#my $decoded_json = decode_json( $response );
#print  $decoded_json->{'id'} . "\n";