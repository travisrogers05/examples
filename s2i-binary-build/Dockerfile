FROM centos:7

# Environment variables
ENV IMAGE_NAME="s2i-binary/testapp" \
    IMAGE_VERSION="1.0" \
    IMAGE_RELEASE="1"

# Labels
LABEL name="$IMAGE_NAME" \
      version="$IMAGE_VERSION" \
      release="$IMAGE_RELEASE" \
      architecture="x86_64" \
      io.k8s.description="Test app demonstrating an S2I binary build" \
      io.k8s.display-name="S2I Binary Test App"

USER root
CMD ["/bin/bash"]
