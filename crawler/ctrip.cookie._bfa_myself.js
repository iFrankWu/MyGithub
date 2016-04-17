
window.replace = function() {
    return ''
};
__trim__ = {};
__bfa = "";
var cookieFun = function(f, C) {

        if (!f.$_bf || !0 !== f.$_bf.loaded) {
            f.$_bf = f.$_bf || {};
            f.$_bf.version = '2.3.8';
            f.$_bf.loaded = !0;
            var p = f.document,
                v = p.location,
                c = c || {},
                I = Object.prototype.toString,
                J = Object.prototype.hasOwnProperty,
                w = function() {},
                D = {
                    undefined: 'undefined',
                    number: 'number',
                    'boolean': 'boolean',
                    string: 'string',
                    '[object Function]': 'function',
                    '[object RegExp]': 'regexp',
                    '[object Array]': 'array',
                    '[object Date]': 'date',
                    '[object Error]': 'error',
                    '[object Object]': 'object'
                };
            c.PROTOCOL = (-1 != v.protocol.indexOf('file:') ?
                'http:' : v.protocol);
            c.ENV = f.$_bf.env || 'online';
            c.ishttps = ('https:' == c.PROTOCOL ? !0 : !1);
            c.CFG = {
                domain: v.hostname.split('.').slice(-2).join('.') || 'ctrip.com',
                referrer: !1,
                cv1: !1,
                orderID: '',
                cookiePath: '/',
                surl: c.PROTOCOL + '//s.c-ctrip.com/',
                href: v.href,
                dCollect: {},
                delay: 0,
                debug: !1
            };
            c.now = Date.now || function() {
                return (new Date).getTime()
            };
            c.type = function(a) {
                return D[typeof a] || D[I.call(a)] || ((a ? 'object' : 'null'))
            };
            c.isArray = function(a) {
                return 'array' === c.type(a)
            };
            c.isFunction = function(a) {
                return 'function' === c.type(a)
            };
            c.isNumber = function(a) {
                return 'number' === typeof a && isFinite(a)
            };
            c.isEmpty = function(a) {
                return a === C || '-' == a || '' == a
            };
            c.getRand = function() {
                return ('' + Math.random()).slice(-8)
            };

            c.hash = function(a) {
                var b = 1,
                    d = 0,
                    e;
                if (!c.isEmpty(a))
                    for (b =
                        0, e = a.length - 1; 0 <= e; e--) d = a.charCodeAt(e),
                        b = (b << 6 & 268435455) + d + (d << 14),
                        d = b & 266338304,
                        b = (0 != d ? b ^ d >> 21 : b);
                return b
            };
            c.$ = function(a) {
                return p.getElementById(a)
            };
            c.$v = function(a) {
                return (a = c.$(a)) && a.value || ''
            };
            c.encode = function(a) {
                return encodeURIComponent(a)
            };
            c.decode = function(a) {
                return decodeURIComponent(a)
            };
            c.contains = function(a, b) {
                return -1 < a.indexOf(b)
            };
            c.getCookie = function(a, b, d) {
                return ((a = p.cookie.match(RegExp('(^| )' + a + '=([^;]*)(;|$)'))) ? (d ? c.decode(a[2]) : a[2]) : b || '')
            };
            c.getCookieObj = function(a, b) {
                var d = {
                        __k: a
                    },
                    e = [],
                    l;
                if (e = c.getCookie(a, '', b))
                    for (var e = e.split('&') || [], q = 0; q < e.length; q++) l = e[q].split('='),
                        1 < l.length && (d[l[0]] = l[1]);
                return d
            };

            c.setCookie = function(a, b, d) {
                var e = (c.CFG.domain ? ';domain=' + c.CFG.domain : ''),
                    l = '';
                0 <= d && (l = ';expires=' + (new Date(c.now() + d)).toUTCString());
                // console.log("Set cookie:"+a + '=' + c.encode(b) + e + ';path=/' + l);
                __bfa = c.encode(b);
                // p.cookie = a + '=' + c.encode(b) + e + ';path=/' + l;

            };
            c.getItem = function(a) {};
            c.get = function(a, b) {
                var d,
                    e = {};
                b = b || {};
                e.v = b.v || '';
                e.w = b.w || 'default';
                e.l = b.l || 0;
                e.t = b.t || 'string';
                switch (e.w) {
                    case 'input':
                        d = c.$v(a);
                        break;
                    case 'cookie':
                        d = c.getCookie(a, e.v);
                        break;
                    case 'function':
                        d = (c.isFunction(e.v) ? e.v() : '');
                        break;
                    case 'object':
                        break;
                    default:
                        d = e.v
                }
                switch (e.t) {
                    case 'number':
                        return parseInt(e.v, 10) || 0;
                    case 'boolean':
                        return !!d;
                    default:
                        return (e.l ? String(d).substring(0, e.l) : d)
                }
            };

            c.CLI = function() {
                var a = f.screen,
                    b = f.navigator,
                    d = (a ? a.colorDepth + '-bit' : ''),
                    e = ((b && b.language ? b.language : (b && b.browserLanguage ? b.browserLanguage : ''))).toLowerCase(),
                    l = (b && b.javaEnabled() ? 1 : 0);
                return {
                    s: a,
                    c: d,
                    l: e,
                    j: l,
                    getRefer: function() {
                        if (c.CFG.referrer) return c.CFG.referrer;
                        var a = '';
                        try {
                            a = p && p.referrer
                        } catch (b) {}
                        if (!a) try {
                            f.opener && (a = f.opener.location && f.opener.location.href)
                        } catch (e) {}
                        c.CFG.referrer = String(a).substring(0, 500);
                        return c.CFG.referrer
                    },
                    getHash: function() {

                        for (var q = f.history.length, n = [
                                b.appName,
                                b.appVersion,
                                e,
                                b.platform,
                                b.userAgent,
                                l,
                                a.width + a.height,
                                d,
                                (p.cookie ? p.cookie : ''),
                                (p.referrer ? p.referrer : '')
                            ].join(''), g = n.length; 0 < q;) n += q-- ^ g++;
                        return c.hash(n)
                    }
                }
            }();

           
            c.isSupportCookie = !0;
      
            c._union_ = !0;
      

            var y = {
                    _env: (/((test[a-z]?|dev|uat|ui|local)\.sh\.(ctrip|huixuan)travel)|(qa\.nt\.ctripcorp)|(127.0.0.1)|(localhost)/i),
                    _bfa: (/^\d.+(\.\d+){2,}$/),
                    _bfs: (/^\d+\.\d+$/),
                    _var: (/\$\{(\w+)\}/g)
                },
                t = function() {
                    this.isEventInit =
                        this.isPSSend = this.isPVSend = !1;
                    this.enterTime = c.now();
                    this.commonData = [];
                    this.eSkip = this.pid = 0;
                    this.envInit_();
                    this.init()
                };
            t.prototype = {
                constructor: t,
                _QUEUE: [],
                init: function() {
                    this.isPVSend = !1;
                    this.bfi = this.bfs = this.bfa = null;
                    this.isLogin = this._isNewSession = this._isNewVisitor = 0;
                    this.readBfa();
                    this.sessRead();
                },
                envInit_: function() {
                    var a = v.hostname;
                    ('test' == c.ENV || y._env.test(a) ? c.CFG.surl = c.PROTOCOL + '//ubt.uat.qa.nt.ctripcorp.com/' : 'offline' == c.ENV && (c.CFG.surl = c.PROTOCOL + '//ubt.sh.ctriptravel.com/'));
                    if (a = this.domainInit(a)) c.CFG.domain = a
                },
                inputInit: function() {
                    try {
                        var a = {},
                            b = this;
                        document.body.innerHTML.replace((/\bbf_ubt_([^ '"]*)/gi), function(d, l) {
                            var f = c.$v(d);
                            if (f) {
                                var h = l.split('_');
                                (h[0] && 'tl' == h[0] ? (('offline' == c.ENV ? 'callid' == h[1] && b.sendData({
                                    k: 'pvctm',
                                    v: '{"callid":"' + f + '"}'
                                }) : b._setPVCustom(h[1], f)), a[h[1]] = f) : (a[h[0]] = f, b.set_(h[0].toLowerCase(), f)))
                            }
                        });
                        'offline' == c.ENV && a.eid && b.sendData({
                            k: 'offline_order',
                            v: '{"uid":"' + (a.uid || '') + '","orderid":"' + (a.orderid || '') + '","callid":"' + a.callid +
                                '","eid":"' + a.eid + '","pid":${page_id},"order_type":"' + (a.ordertype || '') + '"}'
                        })
                    } catch (d) {}
                },
                domainInit: function(a) {
                    a = a || location.hostname;
                    if ((/^(\d|\.)+$/).test(a)) return a;
                    var b = '',
                        c = a.split('.').reverse(),
                        b = c[1] + '.' + c[0];
                    (/(?:\.co)m?\.\w{2,}$/i).test(a) && (b = c[2] + '.' + b);
                    return b
                },
                uniqueId_: function() {
                    var tmp = c.getRand() ^ c.CLI.getHash() & 2147483647;
                    return c.getRand() ^ c.CLI.getHash() & 2147483647
                },
                plus_: function(a) {
                    return parseInt(a, 10) + 1
                },
                setItem: function(a, b, d) {
                    if (c.isSupportCookie) c.setCookie(a, b, d);
                    else return !1
                },
                getItem: function(a, b, d) {
                    return (c.isSupportCookie ?
                        c.getCookie(a, b, !0) : '')
                },
                readBfa: function() {
                    var a = this.getItem('_bfa', '', !0);
                    a && y._bfa.test(a) && (a = a.split('.'), 6 < a.length && (this.bfa = a));
                    this.bfa || (a = this.enterTime, this.bfa = [
                        1,
                        a,
                        this.uniqueId_().toString(36),
                        1,
                        a,
                        a,
                        0,
                        0
                    ], this._isNewVisitor = 1)
                },
                sessRead: function() {
                    var a = this.getItem('_bfs', '', !0);
                    (
                        !this._isNewVisitor && a && y._bfs.test(a) ? 
                        (   
                            this.bfs = a.split('.'), 
                            this.bfs[1] = this.plus_(this.bfs[1])) 
                        : 
                        (this._isNewSession = 1, 
                            this.bfs = [ 1,1], 
                            this.bfa[4] = this.bfa[5], 
                            this.bfa[5] = this.enterTime, 
                            this.bfa[6] = this.plus_(this.bfa[6]))
                        );
                    this.bfa[7] = this.plus_(this.bfa[7]);
                    this.sessWrite()
                },
                sessWrite: function() {
                    this.setItem('_bfa', this.bfa.join('.'), 63072000000);

                },
            };
            var h = new t;

        }
        return __bfa;
}
cookieFun(window);