cmake_minimum_required(VERSION 3.4.1)

# ding yi cpp yuan wen jian mulu bian liang
set(SRC_DIR ${PROJECT_SOURCE_DIR}/src/main/cpp)
set(COMMON_DIR ${SRC_DIR}/common)
set(OPENSLES_DIR ${SRC_DIR}/opensles)

# she zhi cpp yuan ma mu lu
aux_source_directory(${SRC_DIR} src_cpp)
aux_source_directory(${COMMON_DIR} com_cpp)
aux_source_directory(${OPENSLES_DIR} opensles_cpp)

# she zhi .h tou wen jian mu lu
include_directories(COMMON_DIR)
include_directories(OPENSLES_DIR)

# yin ru android log ku
find_library( # Sets the name of the path variable.
              log-lib

              # Specifies the name of the NDK library that
              # you want CMake to locate.
              log )

# ding yi jiang gai ku da bao jin app de lei xing
add_library(adMedia SHARED
        ${src_cpp}
        ${com_cpp}
        ${opensles_cpp}
        )
target_link_libraries(adMedia android log
        OpenSLES
        mediandk
        ${log-lib}
    )