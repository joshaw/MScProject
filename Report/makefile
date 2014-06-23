# Created:  Sun 15 Jun 2014 04:30 pm
# Modified: Mon 23 Jun 2014 09:25 pm

PROJECT=mainfile
TEX=pdflatex
OPTIONS=-interaction nonstopmode\
		-file-line-error\
		-output-directory=$(DIR)\
		-halt-on-error
BIBTEX=bibtex -terse
DIR=build
BUILDTEX=$(TEX) $(OPTIONS) $(PROJECT).tex

CHECKDIR=mkdir -p $(DIR)

all:
	$(CHECKDIR)
	@echo "$(BUILDTEX)"
	@! $(BUILDTEX) | egrep -A 4 ":[0-9]*:|LaTeX Error"
	@echo "$(BIBTEX) $(DIR)/$(PROJECT)"
	@$(BIBTEX) $(DIR)/$(PROJECT) || true
	@echo "$(BUILDTEX)"
	@$(BUILDTEX) > /dev/null
	@echo "$(BUILDTEX)"
	@$(BUILDTEX) > /dev/null
	@xdotool search $(PROJECT).pdf key r > /dev/null 2>&1 || true

once:
	$(BUILDTEX)

clean-all:
	find ${DIR}/ -type f -delete

clean:
	find ${DIR}/ -type f ! -name "*.pdf" -delete

# vim:ft=make
