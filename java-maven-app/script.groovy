def buildApp() {
    echo 'building the application...'
}

def testApp() {
    echo 'testing the application...'
}

def deployApp() {
    echo 'deploying the application...'
    echo "Deploying version ${params.VERSION}"
}

return this
