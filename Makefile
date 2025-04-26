# 📝 Grab the first command-line argument as the input file (no extension)
INPUT_FILE := $(firstword $(MAKECMDGOALS))

# 🛠️ CLI tool used to generate HDL
SCALA_CLI = scala-cli

# 🎯 Output type: can be 'verilog' or 'systemverilog'
OUTPUT = systemverilog

# 📦 Determine file extension based on OUTPUT type
ifeq ($(OUTPUT),systemverilog)
EXT = sv
else ifeq ($(OUTPUT),verilog)
EXT = v
else
$(error ❌ OUTPUT must be either 'verilog' or 'systemverilog')
endif

# 🚀 Default target
.DEFAULT_GOAL := build

test:
	@echo "🧪 Running tests for: $(INPUT_FILE)"
	@$(SCALA_CLI) test $(INPUT_FILE).scala

# 🏗️ HDL Generation rule
build:
	@mkdir -p results
	@echo "📂 Generating HDL for: $(INPUT_FILE).scala"
	@$(SCALA_CLI) $(INPUT_FILE).scala > results/$(INPUT_FILE).$(EXT)
	@echo "✅ HDL generated: results/$(INPUT_FILE).$(EXT)"


visualize:
	iverilog -o results/$(INPUT_FILE) results/$(INPUT_FILE).$(EXT)
#	verilator --cc results/$(INPUT_FILE).sv --exe $(INPUT_FILE).cpp --trace
# 🛡️ Prevent make from interpreting the input file as a target
$(INPUT_FILE): test build


