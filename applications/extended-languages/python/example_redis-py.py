import redis
import time

r = redis.Redis(
    host='localhost',
    port=6379,
    password='CHANGEME')

key = '122'
input = '{ "account_id": "acct1", "user_id": "nyla","label": "00232-2323-23232", "product_code": "CHECKING", "balance": { "amount": 1000000, "currency": "string" }, "account_routings": [ { "address": "000000", "scheme": "NA" } ], "branch_id": "BranchWood" }'
# input = 'bar'
r.set(key, input)

#

cnt = 8000
tic = time.time()

for x in range(cnt):
  output = r.get(key)

toc = time.time()

timePerGet = ((toc - tic)/cnt)*1000
print "==========================="
print("Average time: {:10.10f} milliseconds".format(timePerGet))
print "==========================="
print "KEY:" , key
print "VALUE:" , output
print "==========================="
