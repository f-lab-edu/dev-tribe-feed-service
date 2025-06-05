local setKey, upCountKey, downCountKey = KEYS[1], KEYS[2], KEYS[3]
local postId, userId = ARGV[1], ARGV[2]

local removedUp = redis.call("SREM", setKey, userId .. ":UPVOTE")
if removedUp == 1 then
    redis.call("ZINCRBY", upCountKey, -1, postId)
end

local removedDown = redis.call("SREM", setKey, userId .. ":DOWNVOTE")
if removedDown == 1 then
    redis.call("ZINCRBY", downCountKey, -1, postId)
end

-- postId의 추천 수/ 비추천수 조회
local upScore = redis.call("ZSCORE", upCountKey, postId)
local downScore = redis.call("ZSCORE", downCountKey, postId)

return {upScore, downScore}