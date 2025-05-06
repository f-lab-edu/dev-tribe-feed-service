local upCountKey, downCountKey = KEYS[1], KEYS[2]
local postId = ARGV[1]

-- postId의 추천 수/ 비추천수 조회
local upScore = redis.call("ZSCORE", upCountKey, postId)
local downScore = redis.call("ZSCORE", downCountKey, postId)

return {upScore, downScore}