# CMAKE generated file: DO NOT EDIT!
# Generated by "NMake Makefiles" Generator, CMake Version 3.20

# Delete rule output on recipe failure.
.DELETE_ON_ERROR:

#=============================================================================
# Special targets provided by cmake.

# Disable implicit rules so canonical targets will work.
.SUFFIXES:

.SUFFIXES: .hpux_make_needs_suffix_list

# Command-line flag to silence nested $(MAKE).
$(VERBOSE)MAKESILENT = -s

#Suppress display of executed commands.
$(VERBOSE).SILENT:

# A target that is always out of date.
cmake_force:
.PHONY : cmake_force

#=============================================================================
# Set environment variables for the build.

!IF "$(OS)" == "Windows_NT"
NULL=
!ELSE
NULL=nul
!ENDIF
SHELL = cmd.exe

# The CMake executable.
CMAKE_COMMAND = "E:\Programs\JetBrains\CLion 2021.2.3\bin\cmake\win\bin\cmake.exe"

# The command to remove a file.
RM = "E:\Programs\JetBrains\CLion 2021.2.3\bin\cmake\win\bin\cmake.exe" -E rm -f

# Escaping for special characters.
EQUALS = =

# The top-level source directory on which CMake was run.
CMAKE_SOURCE_DIR = E:\Projects\DistributedProgramming2021Fall\src\com\company\lab6

# The top-level build directory on which CMake was run.
CMAKE_BINARY_DIR = E:\Projects\DistributedProgramming2021Fall\src\com\company\lab6\cmake-build-debug-visual-studio

# Include any dependencies generated for this target.
include CMakeFiles\lab6.dir\depend.make
# Include the progress variables for this target.
include CMakeFiles\lab6.dir\progress.make

# Include the compile flags for this target's objects.
include CMakeFiles\lab6.dir\flags.make

CMakeFiles\lab6.dir\main.cpp.obj: CMakeFiles\lab6.dir\flags.make
CMakeFiles\lab6.dir\main.cpp.obj: ..\main.cpp
	@$(CMAKE_COMMAND) -E cmake_echo_color --switch=$(COLOR) --green --progress-dir=E:\Projects\DistributedProgramming2021Fall\src\com\company\lab6\cmake-build-debug-visual-studio\CMakeFiles --progress-num=$(CMAKE_PROGRESS_1) "Building CXX object CMakeFiles/lab6.dir/main.cpp.obj"
	mpicxx @<<
 /nologo /TP $(CXX_DEFINES) $(CXX_INCLUDES) $(CXX_FLAGS) /FoCMakeFiles\lab6.dir\main.cpp.obj /FdCMakeFiles\lab6.dir\ /FS -c E:\Projects\DistributedProgramming2021Fall\src\com\company\lab6\main.cpp
<<

CMakeFiles\lab6.dir\main.cpp.i: cmake_force
	@$(CMAKE_COMMAND) -E cmake_echo_color --switch=$(COLOR) --green "Preprocessing CXX source to CMakeFiles/lab6.dir/main.cpp.i"
	mpicxx > CMakeFiles\lab6.dir\main.cpp.i @<<
 /nologo /TP $(CXX_DEFINES) $(CXX_INCLUDES) $(CXX_FLAGS) -E E:\Projects\DistributedProgramming2021Fall\src\com\company\lab6\main.cpp
<<

CMakeFiles\lab6.dir\main.cpp.s: cmake_force
	@$(CMAKE_COMMAND) -E cmake_echo_color --switch=$(COLOR) --green "Compiling CXX source to assembly CMakeFiles/lab6.dir/main.cpp.s"
	mpicxx @<<
 /nologo /TP $(CXX_DEFINES) $(CXX_INCLUDES) $(CXX_FLAGS) /FoNUL /FAs /FaCMakeFiles\lab6.dir\main.cpp.s /c E:\Projects\DistributedProgramming2021Fall\src\com\company\lab6\main.cpp
<<

# Object files for target lab6
lab6_OBJECTS = \
"CMakeFiles\lab6.dir\main.cpp.obj"

# External object files for target lab6
lab6_EXTERNAL_OBJECTS =

lab6.exe: CMakeFiles\lab6.dir\main.cpp.obj
lab6.exe: CMakeFiles\lab6.dir\build.make
lab6.exe: CMakeFiles\lab6.dir\objects1.rsp
	@$(CMAKE_COMMAND) -E cmake_echo_color --switch=$(COLOR) --green --bold --progress-dir=E:\Projects\DistributedProgramming2021Fall\src\com\company\lab6\cmake-build-debug-visual-studio\CMakeFiles --progress-num=$(CMAKE_PROGRESS_2) "Linking CXX executable lab6.exe"
	"E:\Programs\JetBrains\CLion 2021.2.3\bin\cmake\win\bin\cmake.exe" -E vs_link_exe --intdir=CMakeFiles\lab6.dir --rc=C:\PROGRA~2\WI3CF2~1\10\bin\100183~1.0\x86\rc.exe --mt=C:\PROGRA~2\WI3CF2~1\10\bin\100183~1.0\x86\mt.exe --manifests -- C:\PROGRA~2\MIB055~1\2019\ENTERP~1\VC\Tools\MSVC\1428~1.293\bin\Hostx86\x86\link.exe /nologo @CMakeFiles\lab6.dir\objects1.rsp @<<
 /out:lab6.exe /implib:lab6.lib /pdb:E:\Projects\DistributedProgramming2021Fall\src\com\company\lab6\cmake-build-debug-visual-studio\lab6.pdb /version:0.0 /machine:X86 /debug /INCREMENTAL /subsystem:console  kernel32.lib user32.lib gdi32.lib winspool.lib shell32.lib ole32.lib oleaut32.lib uuid.lib comdlg32.lib advapi32.lib 
<<

# Rule to build all files generated by this target.
CMakeFiles\lab6.dir\build: lab6.exe
.PHONY : CMakeFiles\lab6.dir\build

CMakeFiles\lab6.dir\clean:
	$(CMAKE_COMMAND) -P CMakeFiles\lab6.dir\cmake_clean.cmake
.PHONY : CMakeFiles\lab6.dir\clean

CMakeFiles\lab6.dir\depend:
	$(CMAKE_COMMAND) -E cmake_depends "NMake Makefiles" E:\Projects\DistributedProgramming2021Fall\src\com\company\lab6 E:\Projects\DistributedProgramming2021Fall\src\com\company\lab6 E:\Projects\DistributedProgramming2021Fall\src\com\company\lab6\cmake-build-debug-visual-studio E:\Projects\DistributedProgramming2021Fall\src\com\company\lab6\cmake-build-debug-visual-studio E:\Projects\DistributedProgramming2021Fall\src\com\company\lab6\cmake-build-debug-visual-studio\CMakeFiles\lab6.dir\DependInfo.cmake --color=$(COLOR)
.PHONY : CMakeFiles\lab6.dir\depend

