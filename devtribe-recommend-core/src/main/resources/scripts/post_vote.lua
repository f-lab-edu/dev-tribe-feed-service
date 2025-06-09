local setKey, upCountKey, downCountKey = KEYS[1], KEYS[2], KEYS[3]
local postId, userId, voteType = ARGV[1], ARGV[2], ARGV[3]

local addKey = userId..":"..voteType
-- voteType == UPVOTE ? DOWNVOTE : UPVOTE
local remKey = userId..":"..(voteType=="UPVOTE" and "DOWNVOTE" or "UPVOTE")

-- voteType == UPVOTE ? upCountKey : downCountKey
local countAdd = (voteType=="UPVOTE" and upCountKey or downCountKey)
local countRem = (voteType=="UPVOTE" and downCountKey or upCountKey)

if redis.call("SREM", setKey, remKey) == 1 then
  redis.call("ZINCRBY", countRem, -1, postId)
end
if redis.call("SADD", setKey, addKey) == 1 then
  redis.call("ZINCRBY", countAdd, 1, postId)
end

-- postId의 추천 수/ 비추천수 조회
local upScore = redis.call("ZSCORE", upCountKey, postId)
local downScore = redis.call("ZSCORE", downCountKey, postId)

return {upScore, downScore}