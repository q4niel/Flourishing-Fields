package dev.q4niel.flourishing_fields

class StateMachine<T>(defaultAction: T, actions: Map<T, Runnable>) {
    private var _actions: Map<T, Runnable> = actions;
    private var _currentAction: T = defaultAction;

    public fun run(): Unit? = _actions[_currentAction]?.run();

    public fun changeAction(action: T): Unit {
        _currentAction = action;
    }
}