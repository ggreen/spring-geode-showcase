use LWP::UserAgent;
use Data::Dumper;
use LWP::UserAgent;

my $usageError = "Usage: <region> <key> <value-json>\n";
my $region = shift or die $usageError;
my $regionKey = shift or die $usageError;
my $regionJsonValue = shift or die $usageError;
my $url = "http://localhost:8000/geode/v1/$region/$regionKey?op=PUT";

print("key=$regionKey value=$regionJsonValue \n");

my $json = $regionJsonValue;
my $http = new LWP::UserAgent();

$response = $http->put(
    $url,
    'content-type' => 'application/json',    Content => $json);

if ( ! $response->is_success() ) {
    print("ERROR: " . $response->status_line());
}