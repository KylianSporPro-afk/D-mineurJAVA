JAVAC  = javac
JAVA   = java
JFLAGS = -implicit:none -encoding UTF-8
BINDIR = bin
MAIN   = Main

.PHONY: all run clean doc

all: $(BINDIR)
	$(JAVAC) $(JFLAGS) -d $(BINDIR) Case.java Grille.java Sauvegarde.java CouleurCase.java SurvoleurbBouton.java PanneauPreview.java VueGrille.java FenetreFinPartie.java FenetreJeu.java MenuPrincipal.java MenuDifficulte.java ConfigPartie.java Main.java

$(BINDIR):
	mkdir -p $(BINDIR)

run: all
	$(JAVA) -cp $(BINDIR) $(MAIN)

doc:
	mkdir -p doc
	javadoc -d doc -encoding UTF-8 -charset UTF-8 *.java

clean:
	rm -rf $(BINDIR) doc
