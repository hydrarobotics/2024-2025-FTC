#define DEFAULT_VISIBILITY __attribute__ ((visibility ("default")))

#define PRINTF_FORMAT(a, b) __attribute__ ((__format__ (__printf__, a, b)))

#cmakedefine ENABLE_LOGGING @ENABLE_LOGGING@

#cmakedefine ENABLE_DEBUG_LOGGING @ENABLE_DEBUG_LOGGING@

#cmakedefine PLATFORM_POSIX @PLATFORM_POSIX@

#cmakedefine PLATFORM_WINDOWS @PLATFORM_WINDOWS@

#cmakedefine HAVE_NFDS_T @HAVE_NFDS_T@

#cmakedefine HAVE_PIPE2 @HAVE_PIPE2@

#cmakedefine HAVE_PTHREAD_THREADID_NP @HAVE_PTHREAD_THREADID_NP@

#cmakedefine HAVE_CLOCK_GETTIME @HAVE_CLOCK_GETTIME@

#cmakedefine HAVE_PTHREAD_SETNAME_NP @HAVE_PTHREAD_SETNAME_NP@

#cmakedefine HAVE_STRUCT_TIMESPEC @HAVE_STRUCT_TIMESPEC@

#cmakedefine HAVE_ASM_TYPES_H @HAVE_ASM_TYPES_H@

#cmakedefine HAVE_LINUX_NETLINK_H @HAVE_LINUX_NETLINK_H@

#cmakedefine HAVE_SYS_SOCKET_H @HAVE_SYS_SOCKET_H@

#cmakedefine HAVE_SYS_TIME_H @HAVE_SYS_TIME_H@

#cmakedefine HAVE_DECL_EFD_NONBLOCK @HAVE_DECL_EFD_NONBLOCK@

#cmakedefine HAVE_DECL_EFD_CLOEXEC @HAVE_DECL_EFD_CLOEXEC@

#cmakedefine HAVE_EVENTFD @HAVE_EVENTFD@

#cmakedefine HAVE_DECL_TFD_NONBLOCK @HAVE_DECL_TFD_NONBLOCK@

#cmakedefine HAVE_DECL_TFD_CLOEXEC @HAVE_DECL_TFD_CLOEXEC@

#cmakedefine HAVE_TIMERFD @HAVE_TIMERFD@

#cmakedefine HAVE_CLOCK_MONOTONIC @HAVE_CLOCK_MONOTONIC@

#cmakedefine HAVE_PTHREAD_CONDATTR_SETCLOCK @HAVE_PTHREAD_CONDATTR_SETCLOCK@

#cmakedefine HAVE_STRING_H @HAVE_STRING_H@

#cmakedefine HAVE_LIBUDEV @HAVE_LIBUDEV@