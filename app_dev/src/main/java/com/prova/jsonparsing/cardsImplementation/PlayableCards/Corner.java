package com.prova.jsonparsing.cardsImplementation.PlayableCards;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.prova.jsonparsing.enums.Artifact;
import com.prova.jsonparsing.enums.Element;


public class Corner {
            private int id;
            private Element element;
            private Artifact artifact;
            @JsonProperty("isAvailable")
            private boolean isAvailable;

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public Element getElement() {
                return element;
            }

            public void setElement(Element element) {
                this.element = element;
            }

            public Object getArtifact() {
                return artifact;
            }

            public void setArtifact(Artifact artifact) {
                this.artifact = artifact;
            }

            public boolean isAvailable() {
                return isAvailable;
            }

            public void setAvailable(boolean available) {
                isAvailable = available;
            }
        }

