<?php

class Application_Filter_EncodeTags implements Zend_Filter_Interface
{

    public function filter($value)
    {
        return str_replace(array('<', '>'), array('&lt;', '&gt;'), $value);
    }

}
