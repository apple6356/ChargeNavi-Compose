package com.seo.sesac.chargenavi.common

import com.seo.sesac.chargenavi.BuildConfig

const val apiKey = BuildConfig.API_KEY
const val naverClientId = BuildConfig.NAVER_CLIENT_ID
const val naverClientSecret = BuildConfig.NAVER_CLIENT_SCERET

// Default Memory Size = 0.15 ~ 0.2
const val COIL_MEMORY_CACHE_SIZE_PERCENT = 0.1

// Coil Disk Cache Size Setting
const val COIL_DISK_CACHE_DIR_NAME = "coil_file_cache"
const val COIL_DISK_CACHE_MAX_SIZE = 1024 * 1024 * 30

// StarRatingBar
// 별점 크기 (리뷰 조회 시)
const val STAR_SIZE = 20
// 별점 크기 (리뷰 작성 시)
const val WRITE_STAR_SIZE = 50
// 별간 간격
const val STAR_SPACING = 4