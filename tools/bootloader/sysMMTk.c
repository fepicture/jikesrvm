#include "sys.h"

#include "../../../mmtk/api/mmtk.h" // the api of the GC
#include <stdlib.h> // malloc and others
#include <errno.h> // error numbers
#include <string.h> // memcpy & memmove
#include <stdio.h> // idk
#include <sys/mman.h> // mmap

EXTERNAL void sysMemmove(void *dst, const void *src, Extent cnt)
{
  printf("Test2\n");
  TRACE_PRINTF("%s: sysMemmove %p %p %zu\n", Me, dst, src, cnt);
  memmove(dst, src, cnt);
}

EXTERNAL void sysHelloWorld()
{
  printf("Hello, World!");
}

EXTERNAL void sysGCInit(int size){
  gc_init ((size_t) size);
}

EXTERNAL void* sysAlloc(int size, int align, int offset){
  return alloc ((size_t) size, (size_t) align, (size_t) offset);
}
