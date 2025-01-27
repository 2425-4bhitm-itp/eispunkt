/** small library for reactive programming
 * (c) Christian Aberger (2024)
 * @author Christian Aberger
 * https://www.aberger.at
 */
export class Observable {
    constructor(initialState) {
        this.subscriptions = new Set();
        this.proxy = new Proxy(initialState, this);
    }
    get(target, property, receiver) {
        return Reflect.get(target, property, receiver);
    }
    set(target, property, newValue, receiver) {
        const done = Reflect.set(target, property, newValue, receiver);
        this.subscriptions.forEach(sub => {
            sub(target);
        });
        return done;
    }
    subscribe(subscription) {
        this.subscriptions.add(subscription);
        subscription(this.proxy);
    }
    get value() {
        return this.proxy;
    }
}
//# sourceMappingURL=observable.js.map