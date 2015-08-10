// Karma configuration
// Generated on Thu Jul 30 2015 14:31:14 GMT+0100 (WAT)
//module.exports = function(config) {
//    config.set({
//        // base path that will be used to resolve all patterns (eg. files, exclude)
//        basePath: '',
//        // frameworks to use
//        // available frameworks: https://npmjs.org/browse/keyword/karma-adapter
//        frameworks: ['jasmine'],
//        // list of files / patterns to load in the browser
//        files: [
//            'lib/angular.min.js',
//            'lib/angular-mocks.js',
//            'bower_components/angular-ui-router/release/angular-ui-router.js',
//            'bower_components/angular-bootstrap/ui-bootstrap-tpls.js',
//            'bower_components/angular-animate/angular-animate.js',
//            'bower_components/angular-sanitize/angular-sanitize.js',
//            'bower_components/angular-resource/angular-resource.js',
//            'bower_components/bardjs/dist/*.js',
//            'bower_components/toastr/*.js',
//            //     'src/client/app/core/*.js',
//            'src/client/app/*.js',
//            'src/client/app/article/*.js',
//            'src/client/app/article/**/*.js'
//            //   'src/client/app/blocks/**/*.js',
//            //   'src/client/app/core/*.js'
//        ],
//        // list of files to exclude
//        exclude: [
//        ],
//        // preprocess matching files before serving them to the browser
//        // available preprocessors: https://npmjs.org/browse/keyword/karma-preprocessor
//        preprocessors: {
//        },
//        // test results reporter to use
//        // possible values: 'dots', 'progress'
//        // available reporters: https://npmjs.org/browse/keyword/karma-reporter
//        reporters: ['progress'],
//        // web server port
//        port: 9876,
//        // enable / disable colors in the output (reporters and logs)
//        colors: true,
//        // level of logging
//        // possible values: config.LOG_DISABLE || config.LOG_ERROR
//        // || config.LOG_WARN || config.LOG_INFO || config.LOG_DEBUG
//        logLevel: config.LOG_INFO,
//        // enable / disable watching file and executing tests whenever any file changes
//        autoWatch: true,
//        // start these browsers
//        // available browser launchers: https://npmjs.org/browse/keyword/karma-launcher
//        browsers: ['Chrome'],
//        // Continuous Integration mode
//        // if true, Karma captures browsers, runs the tests and exits
//        singleRun: false
//    });
//};


module.exports = function() {
    var client = './src/client/';
    var server = './src/server/';
    var clientApp = client + 'app/';
    var report = './report/';
    var root = './';
    var specRunnerFile = 'specs.html';
    var temp = './.tmp/';
    var wiredep = require('wiredep');
    var bowerFiles = wiredep({devDependencies: true})['js'];
    var bower = {
        json: require('./bower.json'),
        directory: './bower_components/',
        ignorePath: '../..'
    };
    var nodeModules = 'node_modules';

    var config = {
        /**
         * File paths
         */
        // all javascript that we want to vet
        alljs: [
            './src/**/*.js',
            './*.js'
        ],
        build: './build/',
        client: client,
        css: temp + 'styles.css',
        fonts: [bower.directory + 'font-awesome/fonts/**/*.*',
                bower.directory + 'bootstrap/fonts/**/*.*'],
        html: client + '**/*.html',
        htmltemplates: clientApp + '**/*.html',
        images: client + 'images/**/*.*',
        index: client + 'index.html',
        // app js, with no specs
        js: [
                clientApp + '**/*.module.js',
                clientApp + '**/*.js',
                '!' + clientApp + '**/*.spec.js'
        ],
        jsOrder: [
            '**/app.module.js',
            '**/*.module.js',
            '**/*.js'
        ],
        less: client + 'styles/styles.less',
        report: report,
        root: root,
        server: server,
        source: 'src/',
        stubsjs: [
                bower.directory + 'angular-mocks/angular-mocks.js',
                client + 'stubs/**/*.js'
        ],
        temp: temp,

        /**
         * optimized files
         */
        optimized: {
            app: 'app.js',
            lib: 'lib.js'
        },

        /**
         * plato
         */
        plato: {js: clientApp + '**/*.js'},

        /**
         * browser sync
         */
        browserReloadDelay: 1000,

        /**
         * template cache
         */
        templateCache: {
            file: 'templates.js',
            options: {
                module: 'app.core',
                root: 'app/',
                standAlone: false
            }
        },

        /**
         * Bower and NPM files
         */
        bower: bower,
        packages: [
            './package.json',
            './bower.json'
        ],

        /**
         * specs.html, our HTML spec runner
         */
        specRunner: client + specRunnerFile,
        specRunnerFile: specRunnerFile,

        /**
         * The sequence of the injections into specs.html:
         *  1 testlibraries
         *      mocha setup
         *  2 bower
         *  3 js
         *  4 spechelpers
         *  5 specs
         *  6 templates
         */
        testlibraries: [
                nodeModules + '/mocha/mocha.js',
                nodeModules + '/chai/chai.js',
                nodeModules + '/mocha-clean/index.js',
                nodeModules + '/sinon-chai/lib/sinon-chai.js'
        ],
        specHelpers: [client + 'test-helpers/*.js'],
        specs: [clientApp + '**/*.spec.js'],
        serverIntegrationSpecs: [client + '/tests/server-integration/**/*.spec.js'],

        /**
         * Node settings
         */
        nodeServer: './src/server/app.js',
        defaultPort: '8001'
    };

    /**
     * wiredep and bower settings
     */
    config.getWiredepDefaultOptions = function() {
        var options = {
            bowerJson: config.bower.json,
            directory: config.bower.directory,
            ignorePath: config.bower.ignorePath
        };
        return options;
    };

    /**
     * karma settings
     */
    config.karma = getKarmaOptions();

    return config;

    ////////////////

    function getKarmaOptions() {
        var options = {
            files: [].concat(
                bowerFiles,
                config.specHelpers,
                    clientApp + '**/*.module.js',
                    clientApp + '**/*.js',
                    temp + config.templateCache.file,
                config.serverIntegrationSpecs
            ),
            exclude: [],
            coverage: {
                dir: report + 'coverage',
                reporters: [
                    // reporters not supporting the `file` property
                    {type: 'html', subdir: 'report-html'},
                    {type: 'lcov', subdir: 'report-lcov'},
                    {type: 'text-summary'} //, subdir: '.', file: 'text-summary.txt'}
                ]
            },
            preprocessors: {}
        };
        options.preprocessors[clientApp + '**/!(*.spec)+(.js)'] = ['coverage'];
        return options;
    }
};



