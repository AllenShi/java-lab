--
local name=redis.call("get", KEYS[1])
local greet=ARGV[1]
local result=greet.." "..name

-- Check user from activity map, key: activity_id_enroll_list, value: {user_id} -> {user_enroll_info}
local user_enrollment = redis.call("hget", KEYS[1])



local broadcast=redis.call("lrange", KEYS[1], 0,-1)
for _,key in ipairs(broadcast) do 
	redis.call("INCR", key)
	count=count+1
end
return result..' '..count

-- KEYS[1]: key of hash of users
-- ARGV[1]:

local user_enroll_info = redis.call("HGET", KEYS[1], ARGV[1])
if user_enroll_info  then
end
