# Created:  Sun 15 Jun 2014 04:30 pm
# @author Josh Wainwright
# File name : makefile

PROJECT=presentation
TEX=pdflatex
OPTIONS=-file-line-error\
		-output-directory=$(DIR)\
		-halt-on-error
DIR=build
BUILDTEX=latexmk -pdf -pdflatex="$(TEX)" $(OPTIONS) $(PROJECT).tex

TEXFILES=$(wildcard **/*.tex)
SVGPDF=$(addprefix $(DIR)/,$(subst .svg,.pdf,$(wildcard **/*.svg)))
DIAPDF=$(addprefix $(DIR)/,$(subst .dia,.pdf,$(wildcard **/*.dia)))
DOTPDF=$(addprefix $(DIR)/,$(subst .dot,.pdf,$(wildcard **/*.dot)))

all: $(DIR) $(DIR)/$(PROJECT).pdf $(SVGPDF) $(DIAPDF) $(DOTPDF)

$(DIR):
	mkdir $(DIR)
	echo $(DIAPDF)

.SECONDEXPANSION:
$(DIR)/%.pdf: %.dia
	@echo "DIA"
	@echo "$< ==> $@"
	mkdir -p $(shell dirname $@)
	dia -t svg -e "/dev/stdout" $< 2> /dev/null | sed '/\/dev\/stdout/d'\
		| rsvg-convert -f pdf > "$@"
		# | sed 's/CM Roman/CMU Serif/'\

$(DIR)/%.pdf: %.svg
	@echo "SVG"
	@echo "$< ---> $@"
	mkdir -p $(shell dirname $@)
	rsvg-convert -f pdf $< > "$@"

$(DIR)/%.pdf: %.dot
	@echo "DOT"
	@echo "$< ---> $@"
	mkdir -p $(shell dirname $@)
	dot -Tpdf $< -o "$@"

$(DIR)/$(PROJECT).pdf: $(PROJECT).tex $(TEXFILES) $(DIAPDF) $(SVGPDF) $(DOTPDF)
	$(BUILDTEX)
	@xdotool search $(PROJECT).pdf key r > /dev/null 2>&1 || true

once: $(PROJECT).tex
	$(TEX) $(OPTIONS) $(PROJECT).tex

open: all
	rifle $(DIR)/$(PROJECT).pdf

clean:
	rm -r $(DIR)

keep-pdf:
	find ${DIR}/ -type f ! -name "*.pdf" -delete

remove-pdf:
	find -name "$(PROJECT).pdf" -delete
# vim:ft=make
