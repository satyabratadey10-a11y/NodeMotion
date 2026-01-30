cmake_minimum_required(VERSION 3.22.1)

project("nodemotion")

# Define where QuickJS is (downloaded by GitHub Action)
set(QUICKJS_DIR ${CMAKE_SOURCE_DIR}/quickjs)

add_library(
        nodemotion
        SHARED
        native-lib.cpp
        ${QUICKJS_DIR}/quickjs.c
        ${QUICKJS_DIR}/libregexp.c
        ${QUICKJS_DIR}/libbf.c
        ${QUICKJS_DIR}/cutils.c
        ${QUICKJS_DIR}/libunicode.c
)

target_compile_definitions(nodemotion PRIVATE CONFIG_VERSION="2021-03-27")
target_include_directories(nodemotion PRIVATE ${QUICKJS_DIR})

find_library(log-lib log)

target_link_libraries(nodemotion ${log-lib} m)
